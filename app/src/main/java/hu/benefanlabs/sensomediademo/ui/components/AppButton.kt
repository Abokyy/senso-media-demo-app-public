package hu.benefanlabs.sensomediademo.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = TextUnit.Unspecified,
    shape: Shape = MaterialTheme.shapes.medium,
    textColor: Color = MaterialTheme.colors.onSecondary,
    backgroundColor: Color = MaterialTheme.colors.secondary,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    contentPadding: PaddingValues = PaddingValues(horizontal = 25.dp, vertical = 15.dp),
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor
        ),
        contentPadding = contentPadding,
        enabled = enabled,
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            leadingIcon?.let {
                Icon(
                    modifier = Modifier.padding(end = 15.dp),
                    imageVector = it,
                    tint = textColor,
                    contentDescription = null
                )
            }

            Text(
                text = text,
                fontSize = fontSize,
                color = textColor
            )

            trailingIcon?.let {
                Icon(
                    modifier = Modifier.padding(start = 15.dp),
                    imageVector = it,
                    tint = textColor,
                    contentDescription = null
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewDefault() {
    AppButton(text = "Beolvasás", shape = MaterialTheme.shapes.large) {

    }
}

@Preview
@Composable
private fun PreviewLeadingIcon() {
    AppButton(
        text = "Előző",
        leadingIcon = Icons.Default.ChevronLeft,
        shape = MaterialTheme.shapes.large
    ) {

    }
}