package hu.benefanlabs.sensomediademo.data.network

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.URLProtocol

class SensoDemoServiceImpl(
    apiClient: ApiClient
) : SensoDemoService {

    companion object {
        private const val BASE_URL = "demo8790931.mockable.io"
    }

    private val client = apiClient.createClient(BASE_URL, URLProtocol.HTTP)

    override suspend fun getSurvey(): String {
        return client.get {
            url(
                path = "/questionnaire"
            )
        }.body()
    }


}