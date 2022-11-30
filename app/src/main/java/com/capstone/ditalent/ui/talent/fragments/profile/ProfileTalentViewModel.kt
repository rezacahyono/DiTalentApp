package com.capstone.ditalent.ui.talent.fragments.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.ditalent.data.repository.auth.UserRepository
import com.capstone.ditalent.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileTalentViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user: MutableLiveData<ProfileTalentUiState> = MutableLiveData()
    val user: LiveData<ProfileTalentUiState> = _user

    init {
        getProfileMe()
    }

    private fun getProfileMe() {
        viewModelScope.launch {
            userRepository.getMe().collect { result ->
                when (result) {
                    is Result.Success -> _user.value = ProfileTalentUiState(user = result.data)
                    is Result.Loading -> _user.value = ProfileTalentUiState(isLoading = true)
                    is Result.Error -> _user.value =
                        ProfileTalentUiState(isError = true, errorMessage = result.uiText)
                }
            }
        }
    }
}