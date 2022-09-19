package hu.benefanlabs.sensomediademo.ui.screens.survey.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowRow
import hu.benefanlabs.sensomediademo.R
import hu.benefanlabs.sensomediademo.domain.entitites.SurveyQuestion
import hu.benefanlabs.sensomediademo.ui.screens.survey.components.ChoiceSelectorChip
import hu.benefanlabs.sensomediademo.ui.screens.survey.components.SurveyImageView

@ExperimentalMaterialApi
@Composable
fun MultipleChoicePage(
    multipleChoiceQuestion: SurveyQuestion.MultipleChoice,
    selectOption: (String) -> Unit
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        multipleChoiceQuestion.imageUrl?.let { imageUrl ->
            SurveyImageView(imageUrl = imageUrl)
        }

        Text(
            modifier = Modifier.padding(top = 20.dp),
            text = multipleChoiceQuestion.question,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = Modifier.padding(top = 20.dp),
            text = stringResource(R.string.you_can_select_multiple),
            color = MaterialTheme.colors.secondary,
            fontSize = 15.sp
        )

        FlowRow(
            modifier = Modifier.padding(top = 20.dp),
            crossAxisSpacing = 10.dp,
            mainAxisSpacing = 10.dp
        ) {
            multipleChoiceQuestion.options.forEach {
                val isSelected = remember(multipleChoiceQuestion.selectedOptions) {
                    multipleChoiceQuestion.selectedOptions.contains(it)
                }
                ChoiceSelectorChip(
                    title = it,
                    onClick = { selectOption(it) },
                    isSelected = isSelected
                )
            }
        }
    }
}