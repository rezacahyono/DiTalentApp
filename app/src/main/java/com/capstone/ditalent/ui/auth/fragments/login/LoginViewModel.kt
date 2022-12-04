package com.capstone.ditalent.ui.auth.fragments.login

import androidx.lifecycle.*
import com.capstone.ditalent.data.repository.auth.UserRepository
import com.capstone.ditalent.model.User
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.capstone.ditalent.utils.Result

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _loginUiState: MutableLiveData<LoginUiState> = MutableLiveData()
    val loginUiState: LiveData<LoginUiState> = _loginUiState

    val user: LiveData<User?> by lazy { userRepository.user.asLiveData() }

    fun loginWithGoogle(credential: AuthCredential, role: String) {
        viewModelScope.launch {
            userRepository.loginWithGoogle(credential, role).collect { result ->
                when (result) {
                    is Result.Success -> _loginUiState.value = LoginUiState(isSuccess = true)
                    is Result.Loading -> _loginUiState.value = LoginUiState(isLoading = true)
                    is Result.Error -> _loginUiState.value =
                        LoginUiState(isError = true, errorMessage = result.uiText)
                }
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            userRepository.loginWithEmailPassword(email, password).collect { result ->
                when (result) {
                    is Result.Success -> _loginUiState.value = LoginUiState(isSuccess = true)
                    is Result.Loading -> _loginUiState.value = LoginUiState(isLoading = true)
                    is Result.Error -> _loginUiState.value =
                        LoginUiState(isError = true, errorMessage = result.uiText)
                }
            }
        }
    }

}