package hu.benefanlabs.sensomediademo.ui.screens.survey.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.benefanlabs.sensomediademo.R
import hu.benefanlabs.sensomediademo.domain.entitites.SurveyQuestion

@Composable
fun SliderPage(
    sliderQuestion: SurveyQuestion.Slider,
    onSelectNewValue: (Int) -> Unit
) {
    Column {
        Text(text = sliderQuestion.question, fontWeight = FontWeight.Bold)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Row(
                modifier = Modifier.padding(
                    horizontal = 10.dp,
                    vertical = 20.dp
                ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.your_answer), fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.secondary
                )
                Text(
                    modifier = Modifier.padding(end = 32.dp),
                    text = "${sliderQuestion.selectedValue}",
                    fontSize = 26.sp,
                    color = MaterialTheme.colors.secondary
                )
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            shape = RoundedCornerShape(40.dp)
        ) {
            Row(
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${sliderQuestion.startValue}",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.secondary
                )

                val sliderRange = remember {
                    sliderQuestion.startValue.toFloat()..sliderQuestion.endValue.toFloat()
                }

                var currentValue by remember {
                    mutableStateOf(sliderQuestion.startValue.toFloat())
                }

                Slider(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .requiredWidth(200.dp),
                    value = currentValue,
                    valueRange = sliderRange,
                    steps = sliderQuestion.endValue - 1,
                    onValueChange = {
                        currentValue = it
                    },
                    onValueChangeFinished = {
                        onSelectNewValue(currentValue.toInt())
                    },
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colors.secondary,
                        activeTickColor = MaterialTheme.colors.secondary,
                        inactiveTickColor = MaterialTheme.colors.secondaryVariant,
                        activeTrackColor = MaterialTheme.colors.secondary,
                        inactiveTrackColor = MaterialTheme.colors.secondaryVariant
                    )
                )

                Text(
                    text = "${sliderQuestion.endValue}",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.secondary
                )
            }
        }
    }

}