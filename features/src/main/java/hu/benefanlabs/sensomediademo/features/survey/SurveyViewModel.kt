package hu.benefanlabs.sensomediademo.features.survey

import hu.benefanlabs.sensomediademo.core.mvi.BaseViewModel
import hu.benefanlabs.sensomediademo.core.mvi.feature.BaseFeature
import hu.benefanlabs.sensomediademo.domain.entitites.SurveyQuestion
import hu.benefanlabs.sensomediademo.domain.exceptions.AppError
import hu.benefanlabs.sensomediademo.domain.usecases.GetSurveyUseCase
import hu.benefanlabs.sensomediademo.domain.usecases.HandleQuestionAnswerUseCase
import hu.benefanlabs.sensomediademo.domain.usecases.params.HandleQuestionAnswerParams
import hu.benefanlabs.sensomediademo.features.FeaturesModule
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class SurveyViewModel :
    BaseViewModel<SurveyViewModel.Wish, SurveyViewModel.State, SurveyViewModel.News>(), DIAware {

    override val di: DI by lazy { FeaturesModule }

    private val getSurveyUseCase: GetSurveyUseCase by instance()
    private val handleQuestionAnswerUseCase: HandleQuestionAnswerUseCase by instance()

    sealed class Wish {
        data class SetSurveyQuestionAnswer(
            val answer: String
        ) : Wish()

        object NextClicked : Wish()
        object PreviousClicked : Wish()
        object ChangeAnonimity : Wish()
    }

    data class State(
        val surveyQuestionList: List<SurveyQuestion>? = null,
        val loading: Boolean = true,
        val currentQuestionIndex: Int = -1,
        val isAnonymousSelected: Boolean = false,
        val isFinishScreen: Boolean = false
    ) {
        val surveyProgress: Float? = surveyQuestionList?.let {
            if (currentQuestionIndex > -1) {
                (currentQuestionIndex + 1).toFloat() / it.size.toFloat()
            } else null
        }

        val remainingQuestions: Int? = surveyQuestionList?.let {
            it.size - currentQuestionIndex - 1
        }

        val currentQuestion = surveyQuestionList?.let {
            if (currentQuestionIndex < 0)
                SurveyQuestion.Anonimity() else surveyQuestionList[currentQuestionIndex]
        }

        val isNextButtonEnabled = currentQuestion?.isAnswered
    }

    sealed class Action {
        object GetSurvey : Action()
        data class SetSurveyQuestionAnswer(
            val answer: String
        ) : Action()

        object NextClicked : Action()
        object PreviousClicked : Action()
        object ChangeAnonimity : Action()
    }

    sealed class Effect {
        data class SurveyGetSuccess(val surveyQuestionList: List<SurveyQuestion>) : Effect()
        data class SurveyGetFailure(val error: AppError) : Effect()
        data class SurveyQuestionAnswerSet(
            val surveyQuestionList: List<SurveyQuestion>
        ) : Effect()

        data class SurveyQuestionAnswerSetFailure(val error: AppError) : Effect()
        object LoadNextPage : Effect()
        object PreviousClicked : Effect()
        object NavigateToFinishScreen : Effect()
        object AnonimityChanged : Effect()
    }

    sealed class News {
        data class HandleError(val error: AppError) : News()
    }

    override val feature: BaseFeature<Wish, Action, Effect, State, News> =
        BaseFeature(
            initialState = State(),
            bootstrapper = {
                flowOf(Action.GetSurvey)
            },
            wishToAction = { wish ->
                when (wish) {
                    Wish.NextClicked -> Action.NextClicked
                    Wish.PreviousClicked -> Action.PreviousClicked
                    is Wish.SetSurveyQuestionAnswer -> Action.SetSurveyQuestionAnswer(
                        answer = wish.answer
                    )
                    Wish.ChangeAnonimity -> Action.ChangeAnonimity
                }
            },
            actor = { state, action ->
                when (action) {
                    Action.GetSurvey -> flow {
                        getSurveyUseCase.invoke(Unit)
                            .foldResult(
                                onSuccess = {
                                    emit(Effect.SurveyGetSuccess(it))
                                },
                                onFailure = {
                                    emit(Effect.SurveyGetFailure(it))
                                }
                            )
                    }
                    Action.NextClicked -> flow {
                        if (state.surveyProgress == 1f) {
                            emit(Effect.NavigateToFinishScreen)
                        } else {
                            emit(Effect.LoadNextPage)
                        }
                    }
                    Action.PreviousClicked -> flowOf(Effect.PreviousClicked)
                    is Action.SetSurveyQuestionAnswer -> flow {
                        handleQuestionAnswerUseCase.invoke(
                            parameters = HandleQuestionAnswerParams(
                                answeredQuestionId = state.currentQuestion?.id,
                                answer = action.answer,
                                questionList = state.surveyQuestionList
                            )
                        ).foldResult(
                            onSuccess = {
                                emit(Effect.SurveyQuestionAnswerSet(it))
                            },
                            onFailure = {
                                emit(Effect.SurveyQuestionAnswerSetFailure(it))
                            }
                        )
                    }
                    Action.ChangeAnonimity -> flowOf(Effect.AnonimityChanged)
                }
            },
            reducer = { state, effect ->
                when (effect) {
                    Effect.LoadNextPage -> {
                        var newQuestionIndex = state.currentQuestionIndex
                        state.surveyQuestionList?.let {
                            newQuestionIndex =
                                if (it.lastIndex == state.currentQuestionIndex) {
                                    -1
                                } else {
                                    state.currentQuestionIndex + 1
                                }
                        }
                        state.copy(
                            currentQuestionIndex = newQuestionIndex
                        )
                    }
                    Effect.PreviousClicked -> {
                        var newQuestionIndex = state.currentQuestionIndex
                        if (state.currentQuestionIndex != -1) {
                            newQuestionIndex = state.currentQuestionIndex - 1
                        }
                        state.copy(
                            currentQuestionIndex = newQuestionIndex
                        )
                    }
                    is Effect.SurveyGetFailure -> state.copy(loading = false)
                    is Effect.SurveyGetSuccess -> state.copy(
                        loading = false,
                        surveyQuestionList = effect.surveyQuestionList
                    )
                    is Effect.SurveyQuestionAnswerSet -> state.copy(
                        surveyQuestionList = effect.surveyQuestionList
                    )
                    Effect.NavigateToFinishScreen -> state.copy(isFinishScreen = true)
                    Effect.AnonimityChanged -> state.copy(
                        isAnonymousSelected = state.isAnonymousSelected.not()
                    )
                    is Effect.SurveyQuestionAnswerSetFailure -> state
                }
            },
            newsPublisher = { _, effect, _ ->
                when (effect) {
                    is Effect.SurveyGetFailure -> News.HandleError(error = effect.error)
                    else -> null
                }
            }
        )
}