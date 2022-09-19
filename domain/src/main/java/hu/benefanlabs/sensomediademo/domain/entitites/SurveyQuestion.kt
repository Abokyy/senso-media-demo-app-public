package hu.benefanlabs.sensomediademo.domain.entitites

sealed class SurveyQuestion(
    open val id: Int,
    open val question: String,
    open val isAnswered: Boolean
) {
    data class SingleChoice(
        override val id: Int,
        override val question: String,
        override val isAnswered: Boolean = false,
        val options: List<String>,
        val selectedOption: String? = null
    ) : SurveyQuestion(id, question, isAnswered)

    data class MultipleChoice(
        override val id: Int,
        override val question: String,
        override val isAnswered: Boolean = false,
        val imageUrl: String? = null,
        val options: List<String>,
        val selectedOptions: List<String> = emptyList()
    ) : SurveyQuestion(id, question, isAnswered)

    data class Slider(
        override val id: Int,
        override val question: String,
        override val isAnswered: Boolean = false,
        val startValue: Int,
        val endValue: Int,
        val selectedValue: Int = startValue
    ) : SurveyQuestion(id, question, isAnswered)

    data class Ranking(
        override val id: Int,
        override val question: String,
        override val isAnswered: Boolean = false,
        val maxValue: Int,
        val selectedValue: Int? = null
    ) : SurveyQuestion(id, question, isAnswered)

    data class OpenEnd(
        override val id: Int,
        override val question: String,
        override val isAnswered: Boolean = false,
        val imageUrl: String? = null,
        val answer: String? = null
    ) : SurveyQuestion(id, question, isAnswered)

    data class Anonimity(
        override val id: Int = -1,
        override val question: String = "",
        override val isAnswered: Boolean = true
    ) : SurveyQuestion(id, question, isAnswered)
}