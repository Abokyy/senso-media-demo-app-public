package hu.benefanlabs.sensomediademo.features.home

import hu.benefanlabs.sensomediademo.core.mvi.BaseViewModel
import hu.benefanlabs.sensomediademo.core.mvi.feature.BaseFeature
import hu.benefanlabs.sensomediademo.domain.exceptions.AppError
import hu.benefanlabs.sensomediademo.domain.usecases.LogoutUserUseCase
import hu.benefanlabs.sensomediademo.features.FeaturesModule
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class HomeViewModel : BaseViewModel<HomeViewModel.Wish, HomeViewModel.State, HomeViewModel.News>(),
    DIAware {

    override val di: DI by lazy { FeaturesModule }

    private val logoutUserUseCase: LogoutUserUseCase by instance()

    sealed class Wish {
        object LogoutUser : Wish()
        data class SetWelcomeMessage(val message: String) : Wish()
        object UnderstoodClicked : Wish()
    }

    data class State(
        val welcomeMessage: String? = null
    )

    sealed class Action {
        object LogoutUser : Action()
        data class SetWelcomeMessage(val message: String) : Action()
        object UnderstoodClicked : Action()
    }

    sealed class Effect {
        object UserLogoutSuccess : Effect()
        data class UserLogoutFailure(val error: AppError) : Effect()
        data class WelcomeMessageSet(val message: String) : Effect()
        object UnderstoodClicked : Effect()
    }

    sealed class News {
        data class HandleError(val error: AppError) : News()
        object LogoutSuccess : News()
    }

    override val feature: BaseFeature<Wish, Action, Effect, State, News> =
        BaseFeature(
            initialState = State(),
            wishToAction = { wish ->
                when (wish) {
                    Wish.LogoutUser -> Action.LogoutUser
                    is Wish.SetWelcomeMessage -> Action.SetWelcomeMessage(message = wish.message)
                    Wish.UnderstoodClicked -> Action.UnderstoodClicked
                }
            },
            actor = { state, action ->
                when (action) {
                    Action.LogoutUser -> flow {
                        logoutUserUseCase.invoke(Unit)
                            .foldResult(
                                onSuccess = {
                                    emit(Effect.UserLogoutSuccess)
                                },
                                onFailure = {
                                    emit(Effect.UserLogoutFailure(it))
                                }
                            )
                    }
                    is Action.SetWelcomeMessage -> flowOf(Effect.WelcomeMessageSet(message = action.message))
                    Action.UnderstoodClicked -> flowOf(Effect.UnderstoodClicked)
                }
            },
            reducer = { state, effect ->
                when (effect) {
                    is Effect.UserLogoutFailure -> state
                    Effect.UserLogoutSuccess -> state
                    is Effect.WelcomeMessageSet -> state.copy(welcomeMessage = effect.message)
                    Effect.UnderstoodClicked -> state.copy(welcomeMessage = null)
                }
            },
            newsPublisher = { _, effect, _ ->
                when (effect) {
                    is Effect.UserLogoutFailure -> News.HandleError(error = effect.error)
                    Effect.UserLogoutSuccess -> News.LogoutSuccess
                    else -> null
                }
            }
        )
}