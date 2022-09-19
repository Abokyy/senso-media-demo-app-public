package hu.benefanlabs.sensomediademo.data.network

interface SensoDemoService {
    suspend fun getSurvey() : String
}