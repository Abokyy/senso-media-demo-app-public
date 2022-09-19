package hu.benefanlabs.sensomediademo

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.startup.AppInitializer
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import hu.benefanlabs.sensomediademo.data.repositories.DataStoreFactoryInitializer

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppInitializer.getInstance(this)
            .initializeComponent(DataStoreFactoryInitializer::class.java)
        setContent {
            SensoMediaDemoApp(
                closeApp = { closeApp() }
            )
        }
    }

    private fun closeApp() {
        this.finishAndRemoveTask()
    }
}