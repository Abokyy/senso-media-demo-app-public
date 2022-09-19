package hu.benefanlabs.sensomediademo

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import hu.benefanlabs.sensomediademo.features.FeaturesModule
import hu.benefanlabs.sensomediademo.ui.navigation.Navigation
import hu.benefanlabs.sensomediademo.ui.theme.SensoMediaDemoTheme
import hu.benefanlabs.sensomediademo.ui.theme.secondaryVariant
import org.kodein.di.compose.withDI

@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalAnimationApi
@Composable
fun SensoMediaDemoApp(
    closeApp: () -> Unit
) = withDI(di = FeaturesModule) {
    val appState = rememberSensoMediaDemoAppState()
    val context = LocalContext.current

    SensoMediaDemoTheme {
        appState.apply {
            Scaffold(
                scaffoldState = scaffoldState,
                snackbarHost = { snackbarHostState ->
                    SnackbarHost(hostState = snackbarHostState) { snackbarData ->
                        Snackbar(snackbarData = snackbarData, backgroundColor = secondaryVariant)
                    }
                }
            ) {
                Navigation(
                    navController = navController,
                    showSnackbar = { message, duration ->
                        showSnackbar(
                            message = message,
                            duration = duration
                        )
                    },
                    closeApp = {
                        showToast(context, context.getString(R.string.app_closed))
                        closeApp()
                    }
                )
            }
        }
    }
}