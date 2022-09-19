package hu.benefanlabs.sensomediademo.data.usecases

import hu.benefanlabs.sensomediademo.data.mapper.SurveyXMLQuestionsToSurveyQuestion
import hu.benefanlabs.sensomediademo.data.network.SensoDemoService
import hu.benefanlabs.sensomediademo.data.network.entities.SurveyXMLRoot
import hu.benefanlabs.sensomediademo.data.network.entities.SurveyXMLRow
import hu.benefanlabs.sensomediademo.domain.UseCase
import hu.benefanlabs.sensomediademo.domain.entitites.SurveyQuestion
import hu.benefanlabs.sensomediademo.domain.usecases.GetSurveyUseCase
import kotlinx.coroutines.CoroutineDispatcher
import org.simpleframework.xml.Serializer
import org.simpleframework.xml.core.Persister

class DefaultGetSurveyUseCase(
    private val service: SensoDemoService,
    coroutineDispatcher: CoroutineDispatcher
) : UseCase<Unit, List<SurveyQuestion>>(coroutineDispatcher), GetSurveyUseCase {
    override suspend fun execute(parameters: Unit): List<SurveyQuestion> {
        val response = service.getSurvey().trimIndent()
        val serializer: Serializer = Persister()
        val surveyXMLRoot = serializer.read(SurveyXMLRoot::class.java, response)

        return surveyXMLRoot.row!!.questions.map {
            SurveyXMLQuestionsToSurveyQuestion().map(it)
        }

    }
}