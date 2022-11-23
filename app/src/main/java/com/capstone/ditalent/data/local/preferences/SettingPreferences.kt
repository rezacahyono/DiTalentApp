package com.capstone.ditalent.data.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingPreferences @Inject constructor(
    private val settingPreference: DataStore<Preferences>
) {

    val isFirstRun: Flow<Boolean> = settingPreference.data.map { preferences ->
        preferences[PreferencesKeys.FIRST_RUN_KEY] ?: true
    }

    suspend fun updateIsFirstRun(value: Boolean) {
        settingPreference.edit { preferences ->
            preferences[PreferencesKeys.FIRST_RUN_KEY] = value
        }
    }

    private object PreferencesKeys {
        val FIRST_RUN_KEY = booleanPreferencesKey("first_run_key")
    }
}