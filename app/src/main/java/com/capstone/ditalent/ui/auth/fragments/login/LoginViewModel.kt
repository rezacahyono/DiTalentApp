package com.capstone.ditalent.ui.auth.fragments.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.ditalent.data.repository.auth.UserRepository
import com.capstone.ditalent.model.User
import com.capstone.ditalent.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val userPref: LiveData<User> by lazy {
        userRepository.userPref.asLiveData()
    }

    fun login(email: String, password: String): LiveData<LoginUiState> =
        userRepository.login(email, password)
            .map { result ->
                LoginUiState(
                    isSuccess = result.keys.first(),
                    isError = !result.keys.first(),
                    errorMessage = result.values.first()
                )
            }
            .onStart { emit(LoginUiState(isLoading = true)) }
            .catch {exception ->
                Log.d("TAG", "setupToLogin: ${exception.message}")
                emit(
                    LoginUiState(
                        isError = true,
                        errorMessage = UiText.DynamicString("Error")
                    )
                )
            }
            .asLiveData()

    fun setUserPref(user: User) {
        viewModelScope.launch {
            userRepository.setUserpref(user)
        }
    }

}