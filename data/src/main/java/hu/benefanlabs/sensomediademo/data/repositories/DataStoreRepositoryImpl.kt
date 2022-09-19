package hu.benefanlabs.sensomediademo.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.startup.Initializer
import kotlinx.coroutines.flow.first

private var appContext: Context? = null
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")

class DataStoreRepositoryImpl : DataStoreRepository {
    val context = appContext!!

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("token")
    }

    override suspend fun saveUserToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    override suspend fun loadUserToken(): String? {
        val preferences = context.dataStore.data.first()
        return preferences[TOKEN_KEY]
    }

    override suspend fun deleteUserToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }
}

class DataStoreFactoryInitializer : Initializer<Context> {
    override fun create(context: Context): Context =
        context.applicationContext.also { appContext = it }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}