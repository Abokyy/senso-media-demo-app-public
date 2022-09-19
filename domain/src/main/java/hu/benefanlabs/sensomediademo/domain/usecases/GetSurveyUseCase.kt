package hu.benefanlabs.sensomediademo.domain.usecases

import hu.benefanlabs.sensomediademo.domain.IUseCase
import hu.benefanlabs.sensomediademo.domain.entitites.SurveyQuestion

interface GetSurveyUseCase : IUseCase<Unit, List<SurveyQuestion>>