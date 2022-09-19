package hu.benefanlabs.sensomediademo.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CloseAppBackHandler(
    closeApp: () -> Unit
) {
    var backClickCount by remember {
        mutableStateOf(0)
    }
    val backHandlerCoroutineScope = rememberCoroutineScope()
    BackHandler {
        backClickCount++
        if (backClickCount == 2) {
            closeApp()
        }
        backHandlerCoroutineScope.launch {
            delay(1000)
            backClickCount = 0
        }
    }
}