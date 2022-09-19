package hu.benefanlabs.sensomediademo.ui.screens.survey

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import hu.benefanlabs.sensomediademo.R
import hu.benefanlabs.sensomediademo.domain.entitites.SurveyQuestion
import hu.benefanlabs.sensomediademo.features.survey.SurveyViewModel
import hu.benefanlabs.sensomediademo.ui.components.AppButton
import hu.benefanlabs.sensomediademo.ui.components.AppDialog
import hu.benefanlabs.sensomediademo.ui.navigation.NavigationRoute
import hu.benefanlabs.sensomediademo.ui.screens.survey.components.SurveyProgressBar
import hu.benefanlabs.sensomediademo.ui.screens.survey.components.SurveyTopBar
import hu.benefanlabs.sensomediademo.ui.screens.survey.pages.AnonimityPage
import hu.benefanlabs.sensomediademo.ui.screens.survey.pages.MultipleChoicePage
import hu.benefanlabs.sensomediademo.ui.screens.survey.pages.OpenEndPage
import hu.benefanlabs.sensomediademo.ui.screens.survey.pages.RankingPage
import hu.benefanlabs.sensomediademo.ui.screens.survey.pages.SingleChoicePage
import hu.benefanlabs.sensomediademo.ui.screens.survey.pages.SliderPage
import hu.benefanlabs.sensomediademo.ui.theme.colorError
import hu.benefanlabs.sensomediademo.utils.appDefaultBackground

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun SurveyScreen(
    surveyViewModel: SurveyViewModel,
    navController: NavController,
    showSnackbar: (String, SnackbarDuration) -> Unit
) {
    DisposableEffect(key1 = true) {
        surveyViewModel.onStart()
        onDispose {
            surveyViewModel.onStop()
        }
    }

    val state by surveyViewModel.state.collectAsState()

    var isCancelDialogShowing by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        surveyViewModel.news.collect { news ->
            when (news) {
                is SurveyViewModel.News.HandleError -> showSnackbar("error", SnackbarDuration.Short)
            }
        }
    }

    if (state.isFinishScreen) {
        SurveySummaryScreen(
            navController = navController,
            emailBody = state.surveyQuestionList.toString()
        )
    } else {
        state.currentQuestion?.let { currentQuestion ->
            SurveyScreenContent(
                currentQuestion = currentQuestion,
                surveyProgress = state.surveyProgress ?: 0.0f,
                remainingQuestions = state.remainingQuestions ?: 0,
                isAnonimitySelected = state.isAnonymousSelected,
                setSurveyQuestionAnswer = {
                    surveyViewModel.sendWish(
                        SurveyViewModel.Wish.SetSurveyQuestionAnswer(
                            answer = it
                        )
                    )
                },
                onNextClicked = {
                    surveyViewModel.sendWish(
                        SurveyViewModel.Wish.NextClicked
                    )
                },
                onPreviousClicked = {
                    surveyViewModel.sendWish(
                        SurveyViewModel.Wish.PreviousClicked
                    )
                },
                onAnonimityChange = {
                    surveyViewModel.sendWish(
                        SurveyViewModel.Wish.ChangeAnonimity
                    )
                },
                isNextButtonEnabled = state.isNextButtonEnabled ?: false,
                isCancelDialogShowing = isCancelDialogShowing,
                showCancelDialog = {
                    isCancelDialogShowing = true
                },
                hideCancelDialog = {
                    isCancelDialogShowing = false
                },
                closeSurvey = {
                    navController.navigate(NavigationRoute.Home.destination) {
                        popUpTo(NavigationRoute.Home.destination) {
                            inclusive = true
                        }
                    }
                }
            )
        } ?: run {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .appDefaultBackground()
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun SurveyScreenContent(
    currentQuestion: SurveyQuestion,
    remainingQuestions: Int,
    surveyProgress: Float,
    setSurveyQuestionAnswer: (answer: String) -> Unit,
    isAnonimitySelected: Boolean,
    onAnonimityChange: () -> Unit,
    onNextClicked: () -> Unit,
    isNextButtonEnabled: Boolean,
    onPreviousClicked: () -> Unit,
    isCancelDialogShowing: Boolean,
    showCancelDialog: () -> Unit,
    hideCancelDialog: () -> Unit,
    closeSurvey: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .appDefaultBackground()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.default_horizontal_padding))
        ) {
            SurveyTopBar(
                modifier = Modifier.padding(top = 32.dp),
                title = stringResource(id = R.string.survey),
                onBackClicked = showCancelDialog
            )
            SurveyProgressBar(
                modifier = Modifier.padding(vertical = 20.dp),
                surveyProgress = surveyProgress,
                remainingQuestions = remainingQuestions
            )

            when (currentQuestion) {
                is SurveyQuestion.MultipleChoice -> MultipleChoicePage(
                    multipleChoiceQuestion = currentQuestion,
                    selectOption = setSurveyQuestionAnswer
                )
                is SurveyQuestion.OpenEnd -> OpenEndPage(
                    openEndQuestion = currentQuestion,
                    onAnswerChange = setSurveyQuestionAnswer
                )
                is SurveyQuestion.Ranking -> RankingPage(
                    rankingQuestion = currentQuestion,
                    onSelectNewValue = {
                        setSurveyQuestionAnswer(it.toString())
                    }
                )
                is SurveyQuestion.SingleChoice -> SingleChoicePage(
                    singleChoiceQuestion = currentQuestion,
                    selectOption = setSurveyQuestionAnswer
                )
                is SurveyQuestion.Slider -> SliderPage(
                    sliderQuestion = currentQuestion,
                    onSelectNewValue = {
                        setSurveyQuestionAnswer(it.toString())
                    }
                )
                is SurveyQuestion.Anonimity -> AnonimityPage(
                    isAnonimitySelected = isAnonimitySelected,
                    onAnonimityChange = onAnonimityChange
                )
            }

        }

        if (currentQuestion !is SurveyQuestion.Anonimity) {
            AppButton(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = 45.dp, start = 15.dp),
                backgroundColor = Color.White,
                text = stringResource(R.string.previous),
                leadingIcon = Icons.Default.ChevronLeft,
                textColor = MaterialTheme.colors.secondary,
                onClick = onPreviousClicked
            )
        }

        AppButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 45.dp, end = 13.dp),
            text = stringResource(R.string.next),
            trailingIcon = Icons.Default.ChevronRight,
            onClick = onNextClicked,
            enabled = isNextButtonEnabled
        )

        if (isCancelDialogShowing) {
            AppDialog(
                imageVector = Icons.Default.Warning,
                onDismiss = hideCancelDialog
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.are_you_sure_to_exit),
                        textAlign = TextAlign.Center
                    )
                    AppButton(
                        modifier = Modifier.padding(top = 8.dp),
                        text = stringResource(R.string.exit),
                        backgroundColor = colorError,
                        onClick = closeSurvey
                    )
                    AppButton(
                        modifier = Modifier.padding(top = 8.dp),
                        text = stringResource(R.string.cancel),
                        fontSize = 10.sp,
                        onClick = hideCancelDialog
                    )
                }
            }
        }
    }
}