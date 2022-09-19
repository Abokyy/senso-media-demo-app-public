package hu.benefanlabs.sensomediademo.ui.screens.survey.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@ExperimentalAnimationApi
@Composable
fun SurveyProgressBar(
    modifier: Modifier = Modifier,
    surveyProgress: Float,
    remainingQuestions: Int
) {
    val progressAnim = animateFloatAsState(targetValue = surveyProgress)
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(14.dp)
                .clip(RoundedCornerShape(18.dp)),
            color = MaterialTheme.colors.secondary,
            backgroundColor = MaterialTheme.colors.secondaryVariant,
            progress = progressAnim.value
        )

        AnimatedContent(
            targetState = remainingQuestions,
            transitionSpec = {
                fadeIn() with fadeOut()
            }
        ) { targetCount ->
            Text(
                modifier = Modifier.padding(top = 9.dp),
                text = "Még $targetCount kérdés",
                color = MaterialTheme.colors.secondary
            )
        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
private fun Preview() {
    SurveyProgressBar(surveyProgress = 0.6f, remainingQuestions = 0)
}