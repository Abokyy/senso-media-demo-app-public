package hu.benefanlabs.sensomediademo.data.di

import hu.benefanlabs.sensomediademo.data.network.ApiClient
import hu.benefanlabs.sensomediademo.data.network.SensoDemoService
import hu.benefanlabs.sensomediademo.data.network.SensoDemoServiceImpl
import hu.benefanlabs.sensomediademo.data.repositories.DataStoreRepository
import hu.benefanlabs.sensomediademo.data.repositories.DataStoreRepositoryImpl
import hu.benefanlabs.sensomediademo.data.usecases.DefaultGetSurveyUseCase
import hu.benefanlabs.sensomediademo.data.usecases.DefaultHandleQuestionAnswerUseCase
import hu.benefanlabs.sensomediademo.data.usecases.DefaultLoadSavedUserTokenUseCase
import hu.benefanlabs.sensomediademo.data.usecases.DefaultLoginUserUseCase
import hu.benefanlabs.sensomediademo.data.usecases.DefaultLogoutUserUseCase
import hu.benefanlabs.sensomediademo.domain.usecases.GetSurveyUseCase
import hu.benefanlabs.sensomediademo.domain.usecases.HandleQuestionAnswerUseCase
import hu.benefanlabs.sensomediademo.domain.usecases.LoadSavedUserTokenUseCase
import hu.benefanlabs.sensomediademo.domain.usecases.LoginUserUseCase
import hu.benefanlabs.sensomediademo.domain.usecases.LogoutUserUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val DataModule = DI.Module(name = "DataModule") {

    bind<CoroutineDispatcher>() with singleton { Dispatchers.Default }

    bind<ApiClient>() with singleton { ApiClient() }

    bind<SensoDemoService>() with singleton {
        SensoDemoServiceImpl(
            apiClient = instance()
        )
    }

    bind<DataStoreRepository>() with singleton {
        DataStoreRepositoryImpl()
    }

    bind<GetSurveyUseCase>() with singleton {
        DefaultGetSurveyUseCase(
            service = instance(),
            coroutineDispatcher = instance()
        )
    }

    bind<LoadSavedUserTokenUseCase>() with singleton {
        DefaultLoadSavedUserTokenUseCase(
            dataStoreRepository = instance(),
            coroutineDispatcher = instance()
        )
    }

    bind<LoginUserUseCase>() with singleton {
        DefaultLoginUserUseCase(
            dataStoreRepository = instance(),
            coroutineDispatcher = instance()
        )
    }

    bind<HandleQuestionAnswerUseCase>() with singleton {
        DefaultHandleQuestionAnswerUseCase(
            coroutineDispatcher = instance()
        )
    }

    bind<LogoutUserUseCase>() with singleton {
        DefaultLogoutUserUseCase(
            dataStoreRepository = instance(),
            coroutineDispatcher = instance()
        )
    }
}