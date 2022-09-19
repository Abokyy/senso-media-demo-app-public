package hu.benefanlabs.sensomediademo.ui.screens.login.components

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.CompoundBarcodeView

@ExperimentalMaterialApi
@ExperimentalPermissionsApi
@Composable
fun QRScanner(
    qrScanned: (String) -> Unit,
    closeApp: () -> Unit
) {
    val context = LocalContext.current
    var scanFlag by remember {
        mutableStateOf(false)
    }

    CameraPermission(
        closeApp = closeApp,
        permissionNotAvailableContent = {
            Column(modifier = Modifier.fillMaxSize()) {
                Text("O noes! No Camera!")
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        context.startActivity(
                            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                data = Uri.fromParts("package", context.packageName, null)
                            }
                        )
                    }
                ) {
                    Text("Open Settings")
                }
            }
        }
    ) {
        val compoundBarcodeView = remember {
            CompoundBarcodeView(context).apply {
                val capture = CaptureManager(context as Activity, this)
                capture.initializeFromIntent(context.intent, null)
                this.setStatusText("")
                this.resume()
                capture.decode()
                this.decodeContinuous { result ->
                    if (scanFlag) {
                        return@decodeContinuous
                    }
                    scanFlag = true
                    result.text?.let { barCodeOrQr ->
                        //Do something and when you finish this something
                        //put scanFlag = false to scan another item
                        qrScanned(barCodeOrQr)
                    }
                    //If you don't put this scanFlag = false, it will never work again.
                    //you can put a delay over 2 seconds and then scanFlag = false to prevent multiple scanning

                }
            }
        }
        AndroidView(
            modifier = Modifier,
            factory = { compoundBarcodeView },
        )
    }

}