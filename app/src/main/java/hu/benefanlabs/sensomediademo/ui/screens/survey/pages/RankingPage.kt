package hu.benefanlabs.sensomediademo.ui.screens.survey.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import hu.benefanlabs.sensomediademo.R
import hu.benefanlabs.sensomediademo.domain.entitites.SurveyQuestion
import hu.benefanlabs.sensomediademo.ui.screens.survey.components.RankingStarRow

@Composable
fun RankingPage(
    rankingQuestion: SurveyQuestion.Ranking,
    onSelectNewValue: (Int) -> Unit
) {
    Column {
        Text(text = rankingQuestion.question, fontWeight = FontWeight.Bold)
        RankingStarRow(
            modifier = Modifier.padding(top = 20.dp),
            currentValue = rankingQuestion.selectedValue ?: 0,
            onSelectValue = onSelectNewValue
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 26.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = stringResource(R.string.hate_it))
            Text(text = stringResource(R.string.very_cool))
        }
    }
}