package com.capstone.ditalent.ui.auth.fragments.register

import androidx.lifecycle.*
import com.capstone.ditalent.data.repository.auth.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.capstone.ditalent.utils.Result

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _registerUiState: MutableLiveData<RegisterUiState> = MutableLiveData()
    val registerUiState: LiveData<RegisterUiState> = _registerUiState

    fun register(
        name: String,
        email: String,
        role: String,
        noPhone: String,
        password: String
    ) {
        viewModelScope.launch {
            userRepository.register(name, email, role, noPhone, password).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _registerUiState.value = RegisterUiState(isSuccess = true, message = result.data)
                    }
                    is Result.Loading -> {
                        _registerUiState.value = RegisterUiState(isLoading = true)
                    }
                    is Result.Error -> {
                        _registerUiState.value = RegisterUiState(isError = true, message = result.uiText)
                    }
                }
            }
        }
    }

}