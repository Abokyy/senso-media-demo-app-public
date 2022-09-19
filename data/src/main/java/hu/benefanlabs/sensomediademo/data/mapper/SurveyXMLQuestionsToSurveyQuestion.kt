package hu.benefanlabs.sensomediademo.data.mapper

import hu.benefanlabs.sensomediademo.data.network.entities.SurveyXMLQuestions
import hu.benefanlabs.sensomediademo.domain.entitites.SurveyQuestion

class SurveyXMLQuestionsToSurveyQuestion : Mapper<SurveyXMLQuestions, SurveyQuestion> {

    enum class SurveyQuestionDtoTypes {
        SINGLE_SELECT, MULTI_SELECT, FREE_TEXT, STARS, SLIDER
    }

    override fun map(from: SurveyXMLQuestions): SurveyQuestion = from.run {
        val typeEnum = SurveyQuestionDtoTypes.valueOf(type!!.uppercase())
        val notNullId = id!!
        val notNullQuestion = question ?: ""
        return when (typeEnum) {
            SurveyQuestionDtoTypes.SINGLE_SELECT -> SurveyQuestion.SingleChoice(
                id = notNullId,
                question = notNullQuestion,
                options = answers.map { it.answer!! }
            )
            SurveyQuestionDtoTypes.MULTI_SELECT -> SurveyQuestion.MultipleChoice(
                id = notNullId,
                question = notNullQuestion,
                imageUrl = image,
                options = answers.map { it.answer!! }
            )
            SurveyQuestionDtoTypes.FREE_TEXT -> SurveyQuestion.OpenEnd(
                id = notNullId,
                question = notNullQuestion,
                imageUrl = image
            )
            SurveyQuestionDtoTypes.STARS -> SurveyQuestion.Ranking(
                id = notNullId,
                question = notNullQuestion,
                maxValue = answers.size
            )
            SurveyQuestionDtoTypes.SLIDER -> SurveyQuestion.Slider(
                id = notNullId,
                question = notNullQuestion,
                startValue = answers.first().answer!!.toInt(),
                endValue = answers.last().answer!!.toInt()
            )
        }
    }
}