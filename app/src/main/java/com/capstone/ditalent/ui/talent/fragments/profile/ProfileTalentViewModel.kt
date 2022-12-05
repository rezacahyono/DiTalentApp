package com.capstone.ditalent.ui.talent.fragments.profile

import androidx.lifecycle.*
import com.capstone.ditalent.data.repository.auth.UserRepository
import com.capstone.ditalent.model.User
import com.capstone.ditalent.utils.Result
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileTalentViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val currentUser: LiveData<FirebaseUser> = userRepository.currentUser.asLiveData()

    val getUser: LiveData<User> = Transformations.switchMap(currentUser) {
        userRepository.getUser(it.uid).asLiveData()
    }

    private val _profileTalentUiState: MutableLiveData<ProfileTalentUiState> = MutableLiveData()
    val profiletalentUiState: LiveData<ProfileTalentUiState> = _profileTalentUiState


    fun logout() {
        viewModelScope.launch {
            userRepository.logout().collect { result ->
                when (result) {
                    is Result.Success -> _profileTalentUiState.value =
                        ProfileTalentUiState(isSuccess = true, message = result.data)
                    is Result.Loading -> _profileTalentUiState.value =
                        ProfileTalentUiState(isLoading = true)
                    is Result.Error -> _profileTalentUiState.value =
                        ProfileTalentUiState(isError = true, message = result.uiText)
                }
            }
        }
    }

}