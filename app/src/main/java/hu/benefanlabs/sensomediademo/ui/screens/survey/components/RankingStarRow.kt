package hu.benefanlabs.sensomediademo.ui.screens.survey.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hu.benefanlabs.sensomediademo.ui.theme.colorDisabled
import hu.benefanlabs.sensomediademo.ui.theme.hrAppPrimary

@Composable
fun RankingStarRow(
    modifier: Modifier = Modifier,
    currentValue: Int,
    onSelectValue: (Int) -> Unit
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        for (star in 1..5) {
            val starColor = remember(currentValue) {
                if (star > currentValue) colorDisabled else hrAppPrimary
            }
            IconButton(onClick = { onSelectValue(star) }) {
                Icon(
                    modifier = Modifier.size(56.dp),
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = starColor
                )
            }
        }
    }
}