package com.capstone.ditalent.ui.talent.fragments.profile

import androidx.lifecycle.*
import com.capstone.ditalent.data.repository.auth.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ProfileTalentViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

//    val user: LiveData<User> = userRepository.getUser.asLiveData()

    val firebaseUser: LiveData<ProfileTalentUiState> =
        userRepository.firebaseUser.map { ProfileTalentUiState(firebaseUser = it) }
            .catch { emit(ProfileTalentUiState(isError = true)) }.asLiveData()

    fun getUser(userId: String): LiveData<ProfileTalentUiState> =
        userRepository.getUser(userId).map { ProfileTalentUiState(user = it) }
            .catch { emit(ProfileTalentUiState(isError = true)) }
            .asLiveData()

    val logout: LiveData<Boolean> = userRepository.logout().map {
        it.keys.first()
    }.asLiveData()


//    private val _user: MutableLiveData<ProfileTalentUiState> = MutableLiveData()
//    val user: LiveData<ProfileTalentUiState> = _user
//
//    init {
//        getProfileMe()
//    }
//
//    private fun getProfileMe() {
//        viewModelScope.launch {
//            userRepository.getMe().collect { result ->
//                when (result) {
//                    is Result.Success -> _user.value = ProfileTalentUiState(user = result.data)
//                    is Result.Loading -> _user.value = ProfileTalentUiState(isLoading = true)
//                    is Result.Error -> _user.value =
//                        ProfileTalentUiState(isError = true, errorMessage = result.uiText)
//                }
//            }
//        }
//    }
}