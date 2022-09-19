package hu.benefanlabs.sensomediademo.ui.screens.login

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import hu.benefanlabs.sensomediademo.R
import hu.benefanlabs.sensomediademo.features.login.LoginViewModel
import hu.benefanlabs.sensomediademo.ui.components.AppButton
import hu.benefanlabs.sensomediademo.ui.components.CloseAppBackHandler
import hu.benefanlabs.sensomediademo.ui.navigation.NavigationRoute
import hu.benefanlabs.sensomediademo.ui.screens.login.components.QRScanner
import hu.benefanlabs.sensomediademo.ui.screens.login.components.ScreenIcon
import hu.benefanlabs.sensomediademo.utils.appDefaultBackground

@ExperimentalMaterialApi
@ExperimentalPermissionsApi
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    navController: NavController,
    showSnackbar: (String, SnackbarDuration) -> Unit,
    closeApp: () -> Unit
) {
    DisposableEffect(key1 = loginViewModel) {
        loginViewModel.onStart()
        onDispose {
            loginViewModel.onStop()
        }
    }

    CloseAppBackHandler(closeApp)

    val state by loginViewModel.state.collectAsState()
    val errorMessage = stringResource(id = R.string.something_went_wrong)
    LaunchedEffect(key1 = true) {
        loginViewModel.news.collect { news ->
            when (news) {
                is LoginViewModel.News.HandleError -> showSnackbar(
                    errorMessage,
                    SnackbarDuration.Short
                )
                LoginViewModel.News.NavigateToGifScreen -> navController.navigate(NavigationRoute.Gif.destination) {
                    popUpTo(NavigationRoute.Login.destination) {
                        inclusive = true
                    }
                }
            }
        }
    }

    LoginScreenContent(
        loading = state.loadingLoggedInUser,
        loginUser = { scannedQr ->
            loginViewModel.sendWish(LoginViewModel.Wish.LoginUser(scannedQr))
        },
        onCloseApp = closeApp
    )
}

@ExperimentalMaterialApi
@ExperimentalPermissionsApi
@Composable
private fun LoginScreenContent(
    loading: Boolean,
    loginUser: (scannedQr: String) -> Unit,
    onCloseApp: () -> Unit
) {
    var takingQR by rememberSaveable { mutableStateOf(false) }

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .appDefaultBackground()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!loading) {
                Text(
                    modifier = Modifier.padding(top = 125.dp),
                    text = stringResource(R.string.login),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    modifier = Modifier.padding(top = 20.dp),
                    text = stringResource(R.string.login_screen_message),
                    textAlign = TextAlign.Center, fontSize = 12.sp
                )

                ScreenIcon(
                    modifier = Modifier.padding(top = 80.dp)
                )

                AppButton(
                    modifier = Modifier.padding(top = 80.dp, bottom = 20.dp),
                    text = stringResource(R.string.scan),
                    shape = MaterialTheme.shapes.large,
                    fontSize = 18.sp,
                    contentPadding = PaddingValues(horizontal = 35.dp, vertical = 20.dp)
                ) {
                    takingQR = true
                }
            }
        }
        if (takingQR) {
            BackHandler {
                takingQR = false
            }

            QRScanner(
                qrScanned = {
                    takingQR = false
                    loginUser(it)
                },
                closeApp = onCloseApp
            )
        }
    }

}