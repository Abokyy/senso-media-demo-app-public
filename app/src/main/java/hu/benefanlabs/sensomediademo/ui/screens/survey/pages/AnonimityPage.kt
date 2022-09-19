package hu.benefanlabs.sensomediademo.ui.screens.survey.pages

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import hu.benefanlabs.sensomediademo.R
import hu.benefanlabs.sensomediademo.ui.components.AppRadioButton

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun AnonimityPage(
    isAnonimitySelected: Boolean = false,
    onAnonimityChange: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = stringResource(id = R.string.anonimity_message), fontWeight = FontWeight.Bold)
        Card(
            modifier = Modifier.padding(top = 20.dp),
            shape = RoundedCornerShape(5.dp),
            onClick = onAnonimityChange
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppRadioButton(
                    selected = isAnonimitySelected,
                    onSelect = onAnonimityChange
                )
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = stringResource(R.string.take_as_anonym)
                )
            }
        }
    }
}