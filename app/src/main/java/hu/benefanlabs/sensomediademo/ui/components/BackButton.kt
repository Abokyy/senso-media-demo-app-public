package hu.benefanlabs.sensomediademo.ui.components

import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import hu.benefanlabs.sensomediademo.R

@Composable
fun CustomIconButton(
    modifier: Modifier = Modifier,
    width: Dp = dimensionResource(id = R.dimen.default_icon_button_width),
    height: Dp = 39.dp,
    iconImageVector: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .requiredHeight(height)
            .requiredWidth(width),
        shape = MaterialTheme.shapes.small,
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = iconImageVector,
                contentDescription = null,
                tint = MaterialTheme.colors.secondary
            )
        }
    }
}