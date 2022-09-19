package hu.benefanlabs.sensomediademo.data.usecases

import hu.benefanlabs.sensomediademo.domain.UseCase
import hu.benefanlabs.sensomediademo.domain.entitites.SurveyQuestion
import hu.benefanlabs.sensomediademo.domain.usecases.HandleQuestionAnswerUseCase
import hu.benefanlabs.sensomediademo.domain.usecases.params.HandleQuestionAnswerParams
import kotlinx.coroutines.CoroutineDispatcher

class DefaultHandleQuestionAnswerUseCase(
    coroutineDispatcher: CoroutineDispatcher
) : UseCase<HandleQuestionAnswerParams, List<SurveyQuestion>>(coroutineDispatcher),
    HandleQuestionAnswerUseCase {
    override suspend fun execute(parameters: HandleQuestionAnswerParams): List<SurveyQuestion> {
        val newList = parameters.questionList!!.toMutableList()
        val answeredQuestionId = parameters.answeredQuestionId!!
        val questionIndex = newList.indexOfFirst {
            it.id == answeredQuestionId
        }

        val answer = parameters.answer!!

        var answeredQuestion = newList[questionIndex]

        answeredQuestion = when (answeredQuestion) {
            is SurveyQuestion.MultipleChoice -> {
                val newSelectedOptions = answeredQuestion.selectedOptions.toMutableList()

                if (newSelectedOptions.contains(answer)) {
                    newSelectedOptions.remove(answer)
                } else {
                    newSelectedOptions.add(answer)
                }

                answeredQuestion.copy(selectedOptions = newSelectedOptions, isAnswered = true)
            }
            is SurveyQuestion.OpenEnd -> {
                answeredQuestion.copy(answer = answer, isAnswered = true)
            }
            is SurveyQuestion.Ranking -> {
                answeredQuestion.copy(selectedValue = answer.toInt(), isAnswered = true)
            }
            is SurveyQuestion.SingleChoice -> {
                answeredQuestion.copy(selectedOption = answer, isAnswered = true)
            }
            is SurveyQuestion.Slider -> {
                answeredQuestion.copy(selectedValue = answer.toInt(), isAnswered = true)
            }
            is SurveyQuestion.Anonimity -> answeredQuestion
        }
        newList[questionIndex] = answeredQuestion

        return newList
    }
}