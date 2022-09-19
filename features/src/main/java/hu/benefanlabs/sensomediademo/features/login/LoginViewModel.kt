package hu.benefanlabs.sensomediademo.features.login

import hu.benefanlabs.sensomediademo.core.mvi.BaseViewModel
import hu.benefanlabs.sensomediademo.core.mvi.feature.BaseFeature
import hu.benefanlabs.sensomediademo.domain.exceptions.AppError
import hu.benefanlabs.sensomediademo.domain.usecases.LoadSavedUserTokenUseCase
import hu.benefanlabs.sensomediademo.domain.usecases.LoginUserUseCase
import hu.benefanlabs.sensomediademo.features.FeaturesModule
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class LoginViewModel :
    BaseViewModel<LoginViewModel.Wish, LoginViewModel.State, LoginViewModel.News>(), DIAware {

    override val di: DI by lazy { FeaturesModule }

    private val loginUserUseCase: LoginUserUseCase by instance()
    private val loadSavedUserTokenUseCase: LoadSavedUserTokenUseCase by instance()

    sealed class Wish {
        data class LoginUser(val qrCode: String) : Wish()
    }

    data class State(
        val loadingLoggedInUser: Boolean = true,
        val loadingLogin: Boolean = false
    )

    sealed class Action {
        data class LoginUser(val qrCode: String) : Action()
        object LoadSavedUserToken : Action()
    }

    sealed class Effect {
        object UserLoginSuccess : Effect()
        data class UserLoginFailure(val error: AppError) : Effect()
        object LoginLoading : Effect()
        object SavedUserTokenLoadSuccess : Effect()
        data class SavedUserTokenLoadFailure(val error: AppError) : Effect()
    }

    sealed class News {
        data class HandleError(val error: AppError) : News()
        object NavigateToGifScreen : News()
    }

    override val feature: BaseFeature<Wish, Action, Effect, State, News> =
        BaseFeature(
            initialState = State(),
            bootstrapper = {
                flowOf(
                    Action.LoadSavedUserToken
                )
            },
            wishToAction = { wish ->
                when (wish) {
                    is Wish.LoginUser -> Action.LoginUser(qrCode = wish.qrCode)
                }
            },
            actor = { state, action ->
                when (action) {
                    Action.LoadSavedUserToken -> flow {
                        loadSavedUserTokenUseCase.invoke(
                            Unit
                        ).foldResult(
                            onSuccess = {
                                emit(Effect.SavedUserTokenLoadSuccess)
                            },
                            onFailure = {
                                emit(Effect.SavedUserTokenLoadFailure(it))
                            }
                        )
                    }
                    is Action.LoginUser -> flow {
                        emit(Effect.LoginLoading)
                        loginUserUseCase.invoke(
                            parameters = action.qrCode
                        ).foldResult(
                            onSuccess = {
                                emit(Effect.UserLoginSuccess)
                            },
                            onFailure = {
                                emit(Effect.UserLoginFailure(it))
                            }
                        )
                    }
                }
            },
            reducer = { state, effect ->
                when (effect) {
                    Effect.LoginLoading -> state.copy(loadingLogin = true)
                    is Effect.SavedUserTokenLoadFailure -> state.copy(loadingLoggedInUser = false)
                    Effect.SavedUserTokenLoadSuccess -> state
                    is Effect.UserLoginFailure -> state
                    Effect.UserLoginSuccess -> state
                }
            },
            newsPublisher = { _, effect, _ ->
                when (effect) {
                    Effect.SavedUserTokenLoadSuccess -> News.NavigateToGifScreen
                    is Effect.UserLoginFailure -> News.HandleError(error = effect.error)
                    Effect.UserLoginSuccess -> News.NavigateToGifScreen
                    else -> null
                }
            }
        )
}