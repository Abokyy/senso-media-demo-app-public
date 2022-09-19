package hu.benefanlabs.sensomediademo.domain.usecases.params

import hu.benefanlabs.sensomediademo.domain.entitites.SurveyQuestion

data class HandleQuestionAnswerParams(
    val answeredQuestionId: Int?,
    val answer: String?,
    val questionList: List<SurveyQuestion>?
)


