package hu.benefanlabs.sensomediademo.ui.screens.gif

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import hu.benefanlabs.sensomediademo.R
import hu.benefanlabs.sensomediademo.ui.navigation.NavigationRoute
import hu.benefanlabs.sensomediademo.utils.appDefaultBackground
import kotlinx.coroutines.delay

@Composable
fun GifScreen(
    navController: NavController
) {
    LaunchedEffect(key1 = true) {
        delay(2000)
        navController.navigate(NavigationRoute.Home.destination) {
            popUpTo(NavigationRoute.Gif.destination) {
                inclusive = true
            }
        }
    }

    GifScreenContent()
}

@Composable
private fun GifScreenContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .appDefaultBackground()
    ) {

        val dynamicProperties = rememberLottieDynamicProperties(
            properties = arrayOf(
                rememberLottieDynamicProperty(
                    property = LottieProperty.COLOR,
                    value = MaterialTheme.colors.secondary.hashCode(),
                    keyPath = arrayOf("**")
                )
            )
        )

        val composition by
        rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.welcome_anim))

        LottieAnimation(
            modifier = Modifier
                .requiredSize(300.dp)
                .align(Alignment.Center),
            composition = composition,
            dynamicProperties = dynamicProperties
        )
    }
}