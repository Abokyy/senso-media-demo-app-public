package hu.benefanlabs.sensomediademo.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import hu.benefanlabs.sensomediademo.ui.theme.secondaryVariant

@Composable
fun SmallIconCard(
    modifier: Modifier = Modifier,
    size: Int = 32,
    imagePadding: Int = 9,
    shape: Shape = RoundedCornerShape(4.dp),
    backgroundColor: Color = secondaryVariant,
    imageVector: ImageVector,
    colorFilter: ColorFilter? = null
) {
    Card(
        modifier = modifier
            .size(size = size.dp),
        backgroundColor = backgroundColor,
        shape = shape
    ) {
        Image(
            modifier = Modifier.padding(imagePadding.dp),
            imageVector = imageVector,
            contentDescription = null,
            colorFilter = colorFilter
        )
    }
}