package hu.benefanlabs.sensomediademo.features

import hu.benefanlabs.sensomediademo.data.di.DataModule
import hu.benefanlabs.sensomediademo.features.home.HomeViewModel
import hu.benefanlabs.sensomediademo.features.login.LoginViewModel
import hu.benefanlabs.sensomediademo.features.survey.SurveyViewModel
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.provider
import org.kodein.di.singleton

val FeaturesModule = DI {
    importOnce(DataModule)

    bind<LoginViewModel>() with provider {
        LoginViewModel()
    }

    bind<SurveyViewModel>() with provider {
        SurveyViewModel()
    }

    bind<HomeViewModel>() with singleton {
        HomeViewModel()
    }
}