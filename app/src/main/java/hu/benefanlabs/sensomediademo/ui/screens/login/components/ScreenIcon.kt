package hu.benefanlabs.sensomediademo.ui.screens.login.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.benefanlabs.sensomediademo.R

@Composable
fun ScreenIcon(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier.padding(top = 50.dp),
            painter = painterResource(id = R.drawable.ic_qr), contentDescription = null
        )
        Image(
            modifier = Modifier.padding(start = 80.dp),
            painter = painterResource(id = R.drawable.ic_magnifing_glass),
            contentDescription = null
        )
    }
}

@Preview
@Composable
private fun Preview() {
    ScreenIcon()
}