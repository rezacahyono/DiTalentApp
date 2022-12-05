package com.capstone.ditalent.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.capstone.ditalent.utils.Constant.FIRST_RUN_BOARDING
import com.capstone.ditalent.utils.Constant.FIRST_RUN_HOME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingPreferences @Inject constructor(
    private val settingPreference: DataStore<Preferences>
) {

    val isFirstRunBoarding: Flow<Boolean> = settingPreference.data.map { preferences ->
        preferences[PreferencesKeys.FIRST_RUN_BOARDING_KEY] ?: true
    }

    val isFirstRunHome: Flow<Boolean> = settingPreference.data.map { preferences ->
        preferences[PreferencesKeys.FIRST_RUN_HOME_KEY] ?: true
    }

    suspend fun updateIsFirstRunBoarding(value: Boolean) {
        settingPreference.edit { preferences ->
            preferences[PreferencesKeys.FIRST_RUN_BOARDING_KEY] = value
        }
    }

    suspend fun updateIsFirstRunHome(value: Boolean){
        settingPreference.edit { preferences ->
            preferences[PreferencesKeys.FIRST_RUN_HOME_KEY] = value
        }
    }

    private object PreferencesKeys {
        val FIRST_RUN_BOARDING_KEY = booleanPreferencesKey(FIRST_RUN_BOARDING)
        val FIRST_RUN_HOME_KEY = booleanPreferencesKey(FIRST_RUN_HOME)
    }
}