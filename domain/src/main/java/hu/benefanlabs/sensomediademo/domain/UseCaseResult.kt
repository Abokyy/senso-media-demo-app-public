package hu.benefanlabs.sensomediademo.domain

import hu.benefanlabs.sensomediademo.domain.exceptions.AppError
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

sealed class UseCaseResult<out L : AppError, out R> {
    data class Failure<out L : AppError>(val error: L) : UseCaseResult<L, Nothing>()

    data class Success<out R>(val data: R) : UseCaseResult<Nothing, R>()

    inline fun <Result> foldResult(
        onFailure: (L) -> Result,
        onSuccess: (R) -> Result
    ): Result {
        return when (this) {
            is Failure -> onFailure(error)
            is Success -> onSuccess(data)
        }
    }
}

@ExperimentalCoroutinesApi
inline fun <Result, L : AppError, R> Flow<UseCaseResult<L, R>>.foldResult(
    crossinline onFailure: (L) -> Result,
    crossinline onSuccess: (R) -> Result
): Flow<Result> =
    this.mapLatest {
        it.foldResult(onFailure, onSuccess)
    }

fun <Result, L : AppError, R> UseCaseResult<L, R>.flatMap(
    fn: (R) -> UseCaseResult<L, Result>
): UseCaseResult<L, Result> {
    return when (this) {
        is UseCaseResult.Failure -> UseCaseResult.Failure(error)
        is UseCaseResult.Success -> fn(data)
    }
}
