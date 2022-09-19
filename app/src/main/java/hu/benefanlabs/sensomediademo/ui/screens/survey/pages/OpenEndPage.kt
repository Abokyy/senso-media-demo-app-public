package hu.benefanlabs.sensomediademo.ui.screens.survey.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsWithImePadding
import hu.benefanlabs.sensomediademo.R
import hu.benefanlabs.sensomediademo.domain.entitites.SurveyQuestion
import hu.benefanlabs.sensomediademo.ui.screens.survey.components.SurveyImageView

@Composable
fun OpenEndPage(
    openEndQuestion: SurveyQuestion.OpenEnd,
    onAnswerChange: (String) -> Unit
) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        openEndQuestion.imageUrl?.let { imageUrl ->
            SurveyImageView(imageUrl = imageUrl)
        }
        Text(
            modifier = Modifier.padding(top = 15.dp),
            text = openEndQuestion.question,
            fontWeight = FontWeight.Bold
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .defaultMinSize(minHeight = 325.dp)
                .navigationBarsWithImePadding(),
            value = openEndQuestion.answer ?: "",
            onValueChange = onAnswerChange,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White,
                focusedBorderColor = MaterialTheme.colors.secondary,
                cursorColor = MaterialTheme.colors.secondary
            ),
            placeholder = {
                Text(text = stringResource(R.string.type_your_message_here))
            }
        )
    }
}