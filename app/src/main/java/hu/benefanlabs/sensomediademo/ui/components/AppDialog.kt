package hu.benefanlabs.sensomediademo.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import hu.benefanlabs.sensomediademo.utils.appDefaultBackground

@ExperimentalMaterialApi
@Composable
fun AppDialog(
    surfaceModifier: Modifier = Modifier,
    imageVector: ImageVector,
    onDismiss: () -> Unit = {},
    cardClickEnabled: Boolean = false,
    onCardClick: () -> Unit = {},
    colorFilter: ColorFilter? = null,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = surfaceModifier
            .fillMaxSize()
            .appDefaultBackground()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onDismiss
            ),
        color = Color.Black.copy(alpha = 0.2f)
    ) {
        Box {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
            ) {
                Surface(onClick = onDismiss) {}
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .padding(top = 20.dp)
                        .padding(horizontal = 52.dp),
                    backgroundColor = Color.White,
                    shape = RoundedCornerShape(12.dp),
                    enabled = cardClickEnabled,
                    onClick = onCardClick,
                ) {
                    content()
                }

                SmallIconCard(
                    modifier = Modifier.align(Alignment.TopCenter),
                    imageVector = imageVector,
                    size = 40,
                    colorFilter = colorFilter
                )
            }
        }
        BackHandler(enabled = true) {
            onDismiss()
        }
    }
}