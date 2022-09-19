package hu.benefanlabs.sensomediademo.ui.screens.login.components

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DoNotDisturb
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import hu.benefanlabs.sensomediademo.R
import hu.benefanlabs.sensomediademo.ui.components.AppButton
import hu.benefanlabs.sensomediademo.ui.components.AppDialog


@ExperimentalMaterialApi
@ExperimentalPermissionsApi
@Composable
fun CameraPermission(
    permissionNotAvailableContent: @Composable () -> Unit = { },
    closeApp: () -> Unit,
    content: @Composable () -> Unit = { }
) {
    val context = LocalContext.current
    val doNotShowRationale = rememberSaveable {
        mutableStateOf(false)
    }

    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    PermissionRequired(
        permissionState = cameraPermissionState,
        permissionNotGrantedContent = {
            if (doNotShowRationale.value) {
                AppDialog(imageVector = Icons.Default.Close) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(stringResource(R.string.camera_not_allowed))
                        AppButton(
                            modifier = Modifier.padding(top = 8.dp),
                            text = stringResource(R.string.close_app),
                            backgroundColor = MaterialTheme.colors.error
                        ) {
                            closeApp()
                        }
                    }
                }
            } else {
                Rationale(
                    onDoNotShowRationale = { doNotShowRationale.value = true },
                    onRequestPermission = { cameraPermissionState.launchPermissionRequest() }
                )
            }
        },
        permissionNotAvailableContent = {
            PermissionDenied {
                context.startActivity(
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                )
            }
        },
        content = content
    )
}


@ExperimentalMaterialApi
@Composable
private fun Rationale(
    onDoNotShowRationale: () -> Unit,
    onRequestPermission: () -> Unit
) {
    AppDialog(imageVector = Icons.Default.Camera) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(R.string.camera_permission_rationale))
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onRequestPermission) {
                Text(stringResource(R.string.request_permission))
            }
            Button(onClick = onDoNotShowRationale) {
                Text(stringResource(R.string.dont_show_again))
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun PermissionDenied(
    navigateToSettingsScreen: () -> Unit
) {
    AppDialog(imageVector = Icons.Default.DoNotDisturb) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(R.string.camera_permission_denied)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = navigateToSettingsScreen) {
                Text(stringResource(R.string.open_settings))
            }
        }
    }
}