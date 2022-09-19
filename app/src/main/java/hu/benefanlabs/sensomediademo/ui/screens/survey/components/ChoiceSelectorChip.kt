package hu.benefanlabs.sensomediademo.ui.screens.survey.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.benefanlabs.sensomediademo.ui.theme.hrAppPrimary

@ExperimentalMaterialApi
@Composable
fun ChoiceSelectorChip(
    modifier: Modifier = Modifier,
    title: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    val backgroundColor = remember(isSelected) {
        if (isSelected) {
            hrAppPrimary
        } else {
            Color.White
        }
    }

    val textColor = remember(isSelected) {
        if (isSelected) {
            Color.White
        } else {
            Color.Black
        }
    }
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        backgroundColor = backgroundColor,
        onClick = onClick
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 15.dp),
            text = title,
            color = textColor
        )
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
private fun NotSelectedPreview() {
    ChoiceSelectorChip(title = "Title") {

    }
}

@ExperimentalMaterialApi
@Preview
@Composable
private fun SelectedPreview() {
    ChoiceSelectorChip(title = "Title", isSelected = true) {

    }
}

