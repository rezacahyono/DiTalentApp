package com.capstone.ditalent.ui.activities.boarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.ditalent.data.SettingPreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardingViewModel @Inject constructor(
    private val settingPreferenceRepository: SettingPreferenceRepository
) : ViewModel() {

    val isFirstRun: LiveData<Boolean> = settingPreferenceRepository.isFirstRun.asLiveData()

    fun updateIsFirstRun(value: Boolean) {
        viewModelScope.launch {
            settingPreferenceRepository.updateIsFirstRun(value)
        }
    }
}