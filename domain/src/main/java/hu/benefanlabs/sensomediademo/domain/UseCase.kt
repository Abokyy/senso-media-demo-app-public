package hu.benefanlabs.sensomediademo.domain

import android.util.Log
import hu.benefanlabs.sensomediademo.domain.exceptions.AppError
import hu.benefanlabs.sensomediademo.domain.exceptions.toAppError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class UseCase<in Params, R>(private val coroutineDispatcher: CoroutineDispatcher) {
    suspend operator fun invoke(parameters: Params): UseCaseResult<AppError, R> {
        return runCatching {
            withContext(coroutineDispatcher) {
                execute(parameters)
            }
        }.fold(
            onSuccess = {
                UseCaseResult.Success(it)
            },
            onFailure = {
                Log.d("USE CASE FAILURE", it.stackTraceToString())
                UseCaseResult.Failure(it.toAppError())
            }
        )
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: Params): R
}

interface IUseCase<in Params, R> {
    suspend operator fun invoke(parameters: Params): UseCaseResult<AppError, R>
}
