package hu.benefanlabs.sensomediademo.ui.screens.survey

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import hu.benefanlabs.sensomediademo.R
import hu.benefanlabs.sensomediademo.ui.components.AppButton
import hu.benefanlabs.sensomediademo.ui.navigation.NavigationRoute
import hu.benefanlabs.sensomediademo.ui.theme.colorError
import hu.benefanlabs.sensomediademo.utils.appDefaultBackground

@Composable
fun SurveySummaryScreen(
    navController: NavController,
    emailBody: String
) {
    SurveySummaryScreenContent(
        onFinishClicked = {
            navController.navigate(NavigationRoute.Home.destination) {
                popUpTo(NavigationRoute.Home.destination) {
                    inclusive = true
                }
            }
        },
        emailBody = emailBody
    )
}

@Composable
private fun SurveySummaryScreenContent(
    onFinishClicked: () -> Unit,
    emailBody: String
) {
    val context = LocalContext.current
    BackHandler {}
    Box(
        modifier = Modifier
            .fillMaxSize()
            .appDefaultBackground()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(dimensionResource(id = R.dimen.default_horizontal_padding)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.survey_fill_success),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            val emailSubject = stringResource(R.string.survey_answers)
            AppButton(
                modifier = Modifier.padding(top = 20.dp),
                text = stringResource(R.string.send_in_email),
                backgroundColor = colorError
            ) {
                context.startActivity(
                    Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_SUBJECT, emailSubject)
                        putExtra(Intent.EXTRA_TEXT, emailBody)
                    }
                )
            }
        }
        AppButton(
            modifier = Modifier
                .padding(bottom = 30.dp)
                .align(Alignment.BottomCenter),
            text = stringResource(R.string.finish),
            onClick = onFinishClicked
        )
    }
}