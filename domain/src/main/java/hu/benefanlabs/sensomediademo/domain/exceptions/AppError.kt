package hu.benefanlabs.sensomediademo.domain.exceptions

sealed class AppError {
    object DefaultError : AppError()
    object LoginError : AppError()
    object UserTokenNotFoundError : AppError()
}

fun Throwable.toAppError(): AppError = when (this) {
    is LoginException -> AppError.LoginError
    is UserTokenNotFoundException -> AppError.UserTokenNotFoundError
    else -> AppError.DefaultError
}
