package hu.benefanlabs.sensomediademo.data.repositories

interface DataStoreRepository {
    suspend fun saveUserToken(token: String)
    suspend fun loadUserToken(): String?
    suspend fun deleteUserToken()
}