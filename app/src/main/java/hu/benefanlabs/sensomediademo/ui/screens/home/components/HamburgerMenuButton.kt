package hu.benefanlabs.sensomediademo.ui.screens.home.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.benefanlabs.sensomediademo.ui.components.CustomIconButton

@Composable
fun HamburgerMenuButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    CustomIconButton(
        modifier = modifier,
        iconImageVector = Icons.Default.Menu,
        width = 40.dp,
        height = 40.dp,
        onClick = onClick
    )
}

@Preview
@Composable
private fun Preview() {
    HamburgerMenuButton {

    }
}