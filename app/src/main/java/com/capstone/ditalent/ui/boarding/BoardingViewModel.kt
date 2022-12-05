package com.capstone.ditalent.ui.boarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.ditalent.data.preferences.SettingPreferences
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardingViewModel @Inject constructor(
    private val settingPreferences: SettingPreferences,
) : ViewModel() {

    val isFirstRunBoarding: LiveData<Boolean> = settingPreferences.isFirstRunBoarding.asLiveData()

    fun updateIsFirstRunBoarding(value: Boolean) {
        viewModelScope.launch {
            settingPreferences.updateIsFirstRunBoarding(value)
        }
    }
}