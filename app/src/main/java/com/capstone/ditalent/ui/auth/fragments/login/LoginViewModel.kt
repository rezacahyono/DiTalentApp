package com.capstone.ditalent.ui.auth.fragments.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.ditalent.data.repository.auth.UserRepository
import com.capstone.ditalent.utils.UiText
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val firebaseUser: LiveData<LoginUiState> =
        userRepository.firebaseUser.map { LoginUiState(firebaseUser = it) }
            .catch { emit(LoginUiState(isError = true)) }.asLiveData()

    fun getUser(userId: String): LiveData<LoginUiState> =
        userRepository.getUser(userId).map { LoginUiState(user = it) }
            .catch { emit(LoginUiState(isError = true)) }
            .asLiveData()


    fun loginWithGoogle(credential: AuthCredential, role: String): LiveData<LoginUiState> =
        userRepository.loginWithGoogle(credential, role)
            .map { result ->
                LoginUiState(
                    isSuccess = result.keys.first(),
                    isError = !result.keys.first(),
                    errorMessage = result.values.first()
                )
            }
            .onStart { emit(LoginUiState(isLoading = true)) }
            .catch {
                emit(
                    LoginUiState(
                        isError = true,
                        errorMessage = UiText.DynamicString("Error")
                    )
                )
            }
            .asLiveData()

    fun login(email: String, password: String): LiveData<LoginUiState> =
        userRepository.loginWithEmailPassword(email, password)
            .map { result ->
                LoginUiState(
                    isSuccess = result.keys.first(),
                    isError = !result.keys.first(),
                    errorMessage = result.values.first()
                )
            }
            .onStart { emit(LoginUiState(isLoading = true)) }
            .catch {
                emit(
                    LoginUiState(
                        isError = true,
                        errorMessage = UiText.DynamicString("Error")
                    )
                )
            }
            .asLiveData()

}