package hu.benefanlabs.sensomediademo.data.usecases

import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import hu.benefanlabs.sensomediademo.data.repositories.DataStoreRepository
import hu.benefanlabs.sensomediademo.domain.UseCase
import hu.benefanlabs.sensomediademo.domain.exceptions.LoginException
import hu.benefanlabs.sensomediademo.domain.usecases.LoginUserUseCase
import kotlinx.coroutines.CoroutineDispatcher

private const val SENSO_MEDIA_AUTH_URL = "https://sensomedia.hu/auth?token="

class DefaultLoginUserUseCase(
    private val dataStoreRepository: DataStoreRepository,
    coroutineDispatcher: CoroutineDispatcher
) : UseCase<String?, Unit>(coroutineDispatcher), LoginUserUseCase {
    override suspend fun execute(parameters: String?) {
        val token = validateAndReturnToken(qrCode = parameters!!)
        dataStoreRepository.saveUserToken(token = token)

        Firebase.messaging.subscribeToTopic("default_topic")
            .addOnCompleteListener { task ->
                var msg = "Subscribed"
                if (!task.isSuccessful) {
                    msg = "Subscribe failed"
                }
                Log.d("TAG", msg)
            }
    }

    private fun validateAndReturnToken(qrCode: String): String {
        if (qrCode.contains(SENSO_MEDIA_AUTH_URL)) {
            return qrCode.removePrefix(SENSO_MEDIA_AUTH_URL)
        } else {
            throw LoginException()
        }
    }
}