package hu.benefanlabs.sensomediademo.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import hu.benefanlabs.sensomediademo.R
import hu.benefanlabs.sensomediademo.features.home.HomeViewModel
import hu.benefanlabs.sensomediademo.ui.components.AppButton
import hu.benefanlabs.sensomediademo.ui.components.CloseAppBackHandler
import hu.benefanlabs.sensomediademo.ui.navigation.NavigationRoute
import hu.benefanlabs.sensomediademo.ui.screens.home.components.DrawerItem
import hu.benefanlabs.sensomediademo.ui.screens.home.components.HamburgerMenuButton
import hu.benefanlabs.sensomediademo.ui.screens.home.components.HomeDrawer
import hu.benefanlabs.sensomediademo.utils.appDefaultBackground
import hu.benefanlabs.sensomediademo.utils.isInternetAvailable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel,
    infoMessage: String?,
    showSnackbar: (String, SnackbarDuration) -> Unit,
    closeApp: () -> Unit
) {

    val state by homeViewModel.state.collectAsState()
    val context = LocalContext.current

    CloseAppBackHandler(closeApp = closeApp)

    val errorMessage = stringResource(id = R.string.something_went_wrong)
    val noInternetConnection = stringResource(R.string.no_internet_connection)

    LaunchedEffect(key1 = true) {
        homeViewModel.onStart()

        infoMessage?.let {
            homeViewModel.sendWish(HomeViewModel.Wish.SetWelcomeMessage(message = infoMessage))
        }
        homeViewModel.news.collect { news ->
            when (news) {
                is HomeViewModel.News.HandleError -> showSnackbar(
                    errorMessage,
                    SnackbarDuration.Short
                )
                HomeViewModel.News.LogoutSuccess -> navController.navigate(NavigationRoute.Login.destination) {
                    popUpTo(NavigationRoute.Home.destination) {
                        inclusive = true
                    }
                }
            }
        }
    }

    val drawerScope = rememberCoroutineScope()
    HomeScreenContent(
        drawerScope = drawerScope,
        message = state.welcomeMessage,
        onSurveyMenuItemClicked = {
            if (isInternetAvailable(context)) {
                navController.navigate(NavigationRoute.Survey.destination)
            } else {
                showSnackbar(noInternetConnection, SnackbarDuration.Short)
            }
        },
        logout = {
            homeViewModel.sendWish(HomeViewModel.Wish.LogoutUser)
        },
        understoodClicked = {
            homeViewModel.sendWish(HomeViewModel.Wish.UnderstoodClicked)
        }
    )
}

@Composable
private fun HomeScreenContent(
    drawerScope: CoroutineScope,
    onSurveyMenuItemClicked: () -> Unit,
    logout: () -> Unit,
    understoodClicked: () -> Unit,
    message: String?
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = Modifier.appDefaultBackground(),
        scaffoldState = scaffoldState,
        drawerShape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 20.dp,
            bottomStart = 0.dp,
            bottomEnd = 20.dp
        ),
        drawerContent = {
            HomeDrawer(onItemClicked = { item ->
                when (item) {
                    DrawerItem.SURVEY -> onSurveyMenuItemClicked()
                    DrawerItem.LOGOUT -> logout()
                }
            })
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .appDefaultBackground()
                .padding(horizontal = dimensionResource(id = R.dimen.default_horizontal_padding))
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
                horizontalArrangement = Arrangement.End
            ) {
                HamburgerMenuButton {
                    drawerScope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            }

            Text(
                modifier = Modifier.padding(top = 15.dp),
                text = stringResource(R.string.welcome),
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold
            )

            message?.let {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp), text = message
                )

                AppButton(
                    modifier = Modifier.padding(top = 8.dp),
                    text = stringResource(R.string.understood),
                    onClick = understoodClicked
                )
            }
        }
    }
}