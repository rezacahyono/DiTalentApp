package com.capstone.ditalent.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingPreferenceRepository @Inject constructor(
    private val settingDataStore: DataStore<Preferences>
) {

    val isFirstRun: Flow<Boolean> = settingDataStore.data.map { preferences ->
        preferences[PreferencesKeys.FIRST_RUN_KEY] ?: true
    }

    suspend fun updateIsFirstRun(value: Boolean) {
        settingDataStore.edit { preferences ->
            preferences[PreferencesKeys.FIRST_RUN_KEY] = value
        }
    }

    private object PreferencesKeys {
        val FIRST_RUN_KEY = booleanPreferencesKey("first_run_key")
    }
}