package com.capstone.ditalent.ui.umkm.fragments.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.ditalent.data.repository.auth.UserRepository
import com.capstone.ditalent.ui.talent.fragments.profile.ProfileTalentUiState
import com.capstone.ditalent.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileUmkmViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _profileUmkmState: MutableLiveData<ProfileTalentUiState> = MutableLiveData()
    val profileUmkmState: LiveData<ProfileTalentUiState> = _profileUmkmState

    fun logout() {
        viewModelScope.launch {
            userRepository.logout().collect { result ->
                when (result) {
                    is Result.Success -> _profileUmkmState.value =
                        ProfileTalentUiState(isSuccess = true, message = result.data)
                    is Result.Loading -> _profileUmkmState.value =
                        ProfileTalentUiState(isLoading = true)
                    is Result.Error -> _profileUmkmState.value =
                        ProfileTalentUiState(isError = true, message = result.uiText)
                }
            }
        }
    }
}