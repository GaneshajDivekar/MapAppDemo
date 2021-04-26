package loginapp.di

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.text.clear

interface PreferenceStorage {
    val token: Flow<String>
    suspend fun setToken(token: String)


    /***
     * clears all the stored data
     */
    suspend fun clearPreferenceStorage()
}

@Singleton
class AppPrefsStorage @Inject constructor(
    @ApplicationContext context: Context
) : PreferenceStorage {

    // since @Singleton scope is used, dataStore will have the same instance every time
    private val dataStore: DataStore<Preferences> =
        context.createDataStore(name = "AppPrefStorage")

    private object PreferencesKeys {
        val token = preferencesKey<String>("token")
    }

    override val token: Flow<String>
        get() = dataStore.getValueAsFlow(PreferencesKeys.token, "")

    override suspend fun clearPreferenceStorage() {
        dataStore.edit {
            it.clear()
        }
    }


    override suspend fun setToken(tokens: String) {
        dataStore.setValue(PreferencesKeys.token, tokens)
    }

    /***
     * handy function to save key-value pairs in Preference. Sets or updates the value in Preference
     * @param key used to identify the preference
     * @param value the value to be saved in the preference
     */
    private suspend fun <T> DataStore<Preferences>.setValue(
        key: Preferences.Key<T>,
        value: T
    ) {
        this.edit { preferences ->
            // save the value in prefs
            preferences[key] = value
        }
    }

    /***
     * handy function to return Preference value based on the Preference key
     * @param key  used to identify the preference
     * @param defaultValue value in case the Preference does not exists
     * @throws Exception if there is some error in getting the value
     * @return [Flow] of [T]
     */
    private fun <T> DataStore<Preferences>.getValueAsFlow(
        key: Preferences.Key<T>,
        defaultValue: T
    ): Flow<T> {
        return this.data.catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                // we try again to store the value in the map operator
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            // return the default value if it doesn't exist in the storage
            preferences[key] ?: defaultValue
        }
    }
}