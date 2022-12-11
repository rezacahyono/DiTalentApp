package com.capstone.ditalent.ui.umkm.fragments.home

import androidx.lifecycle.*
import com.capstone.ditalent.data.preferences.SettingPreferences
import com.capstone.ditalent.data.repository.auth.UserRepository
import com.capstone.ditalent.data.repository.talent.TalentRepository
import com.capstone.ditalent.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import com.capstone.ditalent.utils.Result
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeUmkmViewModel @Inject constructor(
    private val talentRepository: TalentRepository,
    private val userRepository: UserRepository,
    private val settingPreferences: SettingPreferences
) : ViewModel() {

    val isFirstRunHome: LiveData<Boolean> = settingPreferences.isFirstRunHome.asLiveData()

    private val currentUser: LiveData<FirebaseUser> = userRepository.currentUser.asLiveData()

    fun getUser(): LiveData<User> = Transformations.switchMap(currentUser){
        userRepository.getUser(it.uid).asLiveData()
    }

    private val _homeUmkmState: MutableLiveData<HomeUmkmUiState> = MutableLiveData()
    val homeUmkmUiState: LiveData<HomeUmkmUiState> = _homeUmkmState


    init {
        getTalents()
    }

    private fun getTalents() {
        viewModelScope.launch {
            talentRepository.getTopUserRoleTalent().collect { result ->
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