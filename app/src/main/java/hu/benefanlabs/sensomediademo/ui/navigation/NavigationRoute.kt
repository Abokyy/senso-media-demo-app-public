package hu.benefanlabs.sensomediademo.ui.navigation

sealed class NavigationRoute(
    val destination: String
) {
    companion object {
        private const val LOGIN_PATH = "login"
        private const val SURVEY_PATH = "survey"
        private const val GIF_PATH = "gif"
        private const val HOME_PATH = "home"
    }

    object Login : NavigationRoute(LOGIN_PATH)
    object Gif : NavigationRoute(GIF_PATH)
    object Survey : NavigationRoute(SURVEY_PATH)
    object Home : NavigationRoute(HOME_PATH)
}