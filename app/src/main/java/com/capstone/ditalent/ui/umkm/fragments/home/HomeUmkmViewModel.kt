package com.capstone.ditalent.ui.umkm.fragments.home

import androidx.lifecycle.*
import com.capstone.ditalent.data.preferences.SettingPreferences
import com.capstone.ditalent.data.repository.talent.TalentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import com.capstone.ditalent.utils.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeUmkmViewModel @Inject constructor(
    private val talentRepository: TalentRepository,
    private val settingPreferences: SettingPreferences
) : ViewModel() {

    val isFirstRunHome: LiveData<Boolean> = settingPreferences.isFirstRunHome.asLiveData()

    private val _homeUmkmState: MutableLiveData<HomeUmkmUiState> = MutableLiveData()
    val homeUmkmUiState: LiveData<HomeUmkmUiState> = _homeUmkmState


    init {
        getTalents()
    }

    private fun getTalents() {
        viewModelScope.launch {
            talentRepository.getUsersRoleTalent().collect { result ->
                when (result) {
                    is Result.Success -> {
                        _homeUmkmState.value = HomeUmkmUiState(talents = result.data)
                    }
                    is Result.Loading -> {
                        _homeUmkmState.value = HomeUmkmUiState(isLoading = true)
                    }
                    is Result.Error -> {
                        _homeUmkmState.value = HomeUmkmUiState(isError = true)
                    }
                }
            }

        }
    }

    fun updateIsFirstRunHome(value: Boolean) {
        viewModelScope.launch {
            settingPreferences.updateIsFirstRunHome(value)
        }
    }
}