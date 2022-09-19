package hu.benefanlabs.sensomediademo.ui.screens.survey.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import hu.benefanlabs.sensomediademo.R
import hu.benefanlabs.sensomediademo.ui.components.CustomIconButton

@Composable
fun SurveyTopBar(
    modifier: Modifier = Modifier,
    title: String,
    onBackClicked: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CustomIconButton(iconImageVector = Icons.Default.ChevronLeft, onClick = onBackClicked)
        Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.default_icon_button_width)))
    }
}

@Preview
@Composable
private fun Preview() {
    SurveyTopBar(title = "Kérdőív") {

    }
}