package hu.benefanlabs.sensomediademo.ui.screens.survey.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import hu.benefanlabs.sensomediademo.domain.entitites.SurveyQuestion
import hu.benefanlabs.sensomediademo.ui.screens.survey.components.ChoiceSelectorChip
import hu.benefanlabs.sensomediademo.ui.theme.hrAppSecondary
import hu.benefanlabs.sensomediademo.ui.theme.secondaryVariant
import hu.benefanlabs.sensomediademo.utils.Constants

@ExperimentalMaterialApi
@Composable
fun SingleChoicePage(
    singleChoiceQuestion: SurveyQuestion.SingleChoice,
    selectOption: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = singleChoiceQuestion.question, fontWeight = FontWeight.Bold)
        LazyColumn(
            modifier = Modifier.padding(top = 20.dp).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(bottom = 120.dp)
        ) {
            itemsIndexed(singleChoiceQuestion.options) { index, option ->
                SingleChoiceOptionRow(
                    optionCharacterIndex = index,
                    optionTitle = option,
                    isSelected = singleChoiceQuestion.selectedOption == option
                ) {
                    selectOption(option)
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun SingleChoiceOptionRow(
    optionCharacterIndex: Int,
    optionTitle: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        val optionCharacter = remember {
            if (optionCharacterIndex > Constants.ALPHABET.length - 1) {
                Constants.ALPHABET[optionCharacterIndex] + optionCharacterIndex
            } else {
                Constants.ALPHABET[optionCharacterIndex]
            }
        }
        Text(text = "$optionCharacter:", color = secondaryVariant)
        ChoiceSelectorChip(
            modifier = Modifier.padding(start = 20.dp),
            title = optionTitle,
            isSelected = isSelected,
            onClick = onSelect
        )
    }
}