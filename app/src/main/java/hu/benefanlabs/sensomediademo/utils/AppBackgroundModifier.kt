package hu.benefanlabs.sensomediademo.utils

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import hu.benefanlabs.sensomediademo.ui.theme.primary
import hu.benefanlabs.sensomediademo.ui.theme.primaryGradient1
import hu.benefanlabs.sensomediademo.ui.theme.primaryGradient2
import hu.benefanlabs.sensomediademo.ui.theme.primaryGradient3

fun Modifier.appDefaultBackground() = this.then(
    this.background(
        brush = Brush.horizontalGradient(
            colors = listOf(
                primaryGradient1,
                primaryGradient3,
            )
        )
    )
)