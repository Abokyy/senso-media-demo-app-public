package hu.benefanlabs.sensomediademo.data.usecases

import hu.benefanlabs.sensomediademo.data.repositories.DataStoreRepository
import hu.benefanlabs.sensomediademo.domain.UseCase
import hu.benefanlabs.sensomediademo.domain.usecases.LoadSavedUserTokenUseCase
import kotlinx.coroutines.CoroutineDispatcher

class DefaultLoadSavedUserTokenUseCase(
    private val dataStoreRepository: DataStoreRepository,
    coroutineDispatcher: CoroutineDispatcher
) : UseCase<Unit, String>(coroutineDispatcher), LoadSavedUserTokenUseCase {
    override suspend fun execute(parameters: Unit): String {
        return dataStoreRepository.loadUserToken()!!
    }
}