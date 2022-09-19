package hu.benefanlabs.sensomediademo.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import hu.benefanlabs.sensomediademo.features.home.HomeViewModel
import hu.benefanlabs.sensomediademo.features.login.LoginViewModel
import hu.benefanlabs.sensomediademo.features.survey.SurveyViewModel
import hu.benefanlabs.sensomediademo.ui.screens.gif.GifScreen
import hu.benefanlabs.sensomediademo.ui.screens.home.HomeScreen
import hu.benefanlabs.sensomediademo.ui.screens.login.LoginScreen
import hu.benefanlabs.sensomediademo.ui.screens.survey.SurveyScreen
import hu.benefanlabs.sensomediademo.ui.screens.survey.SurveySummaryScreen
import org.kodein.di.compose.rememberViewModel

@ExperimentalPermissionsApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun Navigation(
    navController: NavHostController,
    showSnackbar: (message: String, duration: SnackbarDuration) -> Unit,
    closeApp: () -> Unit
) {
    val homeViewModel: HomeViewModel by rememberViewModel()

    NavHost(navController = navController, startDestination = NavigationRoute.Login.destination) {
        composable(NavigationRoute.Login.destination) {
            val loginViewModel: LoginViewModel by rememberViewModel()
            LoginScreen(
                navController = navController,
                loginViewModel = loginViewModel,
                showSnackbar = showSnackbar,
                closeApp = closeApp
            )
        }

        composable(NavigationRoute.Survey.destination) {
            val surveyViewModel: SurveyViewModel by rememberViewModel()
            SurveyScreen(
                surveyViewModel = surveyViewModel,
                navController = navController,
                showSnackbar = showSnackbar
            )
        }

        composable(NavigationRoute.Gif.destination) {
            GifScreen(navController = navController)
        }

        composable(route = NavigationRoute.Home.destination,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "custom://hu.benefenlabs.sensomediademoapp/{info}"
                }
            )) { navBackStackEntry ->
            val infoMessage = navBackStackEntry.arguments?.getString("info")
            HomeScreen(
                navController = navController,
                homeViewModel = homeViewModel,
                showSnackbar = showSnackbar,
                infoMessage = infoMessage,
                closeApp = closeApp
            )
        }
    }
}