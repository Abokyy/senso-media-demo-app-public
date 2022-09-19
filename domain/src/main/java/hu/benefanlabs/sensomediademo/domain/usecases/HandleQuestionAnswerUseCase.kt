package hu.benefanlabs.sensomediademo.domain.usecases

import hu.benefanlabs.sensomediademo.domain.IUseCase
import hu.benefanlabs.sensomediademo.domain.entitites.SurveyQuestion
import hu.benefanlabs.sensomediademo.domain.usecases.params.HandleQuestionAnswerParams

interface HandleQuestionAnswerUseCase :
    IUseCase<HandleQuestionAnswerParams, List<SurveyQuestion>>