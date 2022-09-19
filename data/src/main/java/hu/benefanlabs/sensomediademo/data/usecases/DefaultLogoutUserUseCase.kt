package hu.benefanlabs.sensomediademo.data.usecases

import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import hu.benefanlabs.sensomediademo.data.repositories.DataStoreRepository
import hu.benefanlabs.sensomediademo.domain.UseCase
import hu.benefanlabs.sensomediademo.domain.usecases.LogoutUserUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

class DefaultLogoutUserUseCase(
    private val dataStoreRepository: DataStoreRepository,
    coroutineDispatcher: CoroutineDispatcher
) : UseCase<Unit, Unit>(coroutineDispatcher), LogoutUserUseCase {

    override suspend fun execute(parameters: Unit) {
        dataStoreRepository.deleteUserToken()
        Firebase.messaging.unsubscribeFromTopic("default_topic")
            .addOnCompleteListener { task ->
                var msg = "UNSubscribed"
                if (!task.isSuccessful) {
                    msg = "UNSubscribe failed"
                }
                Log.d("TAG", msg)
            }
    }
}