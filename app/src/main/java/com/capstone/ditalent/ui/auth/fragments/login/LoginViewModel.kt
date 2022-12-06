package com.capstone.ditalent.ui.auth.fragments.login

import androidx.lifecycle.*
import com.capstone.ditalent.data.repository.auth.UserRepository
import com.capstone.ditalent.model.User
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.capstone.ditalent.utils.Result
import com.google.firebase.auth.FirebaseUser

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _loginUiState: MutableLiveData<UiState> = MutableLiveData()
    val loginUiState: LiveData<UiState> = _loginUiState

    private val _logoutUiState: MutableLiveData<UiState> = MutableLiveData()
    val logoutUiState: LiveData<UiState> = _logoutUiState

    val currentUser: LiveData<FirebaseUser> = userRepository.currentUser.asLiveData()

    val getUser: LiveData<User> = Transformations.switchMap(currentUser) {
        userRepository.getUser(it.uid).asLiveData()
    }

    val checkUserIsExists: LiveData<Boolean> =
        Transformations.switchMap(currentUser) {
            liveData {
                val result = userRepository.checkUserIsExists(it.uid)
                emit(result)
            }
        }

    fun loginWithGoogle(credential: AuthCredential) {
        viewModelScope.launch {
            userRepository.loginWithGoogle(credential).collect { result ->
                when (result) {
                    is Result.Success -> _loginUiState.value =
                        UiState(isSuccess = true, message = result.data)
                    is Result.Loading -> _loginUiState.value = UiState(isLoading = true)
                    is Result.Error -> _loginUiState.value =
                        UiState(isError = true, message = result.uiText)
                }
            }
        }
    }

    fun addUser(role: String): LiveData<UiState> = Transformations.switchMap(currentUser) {
        liveData {
            userRepository.addUser(it, role).collect { result ->
                when (result) {
                    is Result.Success ->
                        emit(UiState(isSuccess = true, message = result.data))
                    is Result.Loading -> emit(UiState(isLoading = true))
                    is Result.Error ->
                        emit(UiState(isError = true, message = result.uiText))
                }
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            userRepository.loginWithEmailPassword(email, password).collect { result ->
                when (result) {
                    is Result.Success -> _loginUiState.value =
                        UiState(isSuccess = true, message = result.data)
                    is Result.Loading -> _loginUiState.value = UiState(isLoading = true)
                    is Result.Error -> _loginUiState.value =
                        UiState(isError = true, message = result.uiText)
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout().collect { result ->
                when (result) {
                    is Result.Success -> _logoutUiState.value =
                        UiState(isSuccess = true, message = result.data)
                    is Result.Loading -> _logoutUiState.value = UiState(isLoading = true)
                    is Result.Error -> _logoutUiState.value =
                        UiState(isError = true, message = result.uiText)
                }
            }
        }
    }

}