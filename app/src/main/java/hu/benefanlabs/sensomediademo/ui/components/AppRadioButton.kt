package hu.benefanlabs.sensomediademo.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@ExperimentalAnimationApi
@Preview
@Composable
fun AppRadioButton(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    borderWidth: Dp = 2.dp,
    innerIndicatorPadding: Dp = 8.dp,
    selected: Boolean = false,
    shape: Shape = CircleShape,
    onSelect: (() -> Unit)? = null,
    enabled: Boolean = true,
    hasError: Boolean = false
) {
    val backgroundColor by animateColorAsState(
        targetValue =
        if (hasError) MaterialTheme.colors.error else MaterialTheme.colors.secondary
    )
    Box(
        modifier = modifier
            .clip(shape = shape)
            .requiredSize(size = size)
            .border(width = borderWidth, color = backgroundColor, shape = shape)
            .clickable(
                enabled = enabled,
                onClick = onSelect ?: {}
            ),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = selected,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .clip(shape = shape)
                    .size(size = size - innerIndicatorPadding)
                    .background(color = backgroundColor)
            )
        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun AppRadioButtonSelectedPreview() {
    AppRadioButton(selected = true)
}

@ExperimentalAnimationApi
@Preview
@Composable
fun AppRadioButtonRectSelectedPreview() {
    AppRadioButton(selected = true, shape = RoundedCornerShape(3.dp))
}
