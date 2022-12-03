package com.capstone.ditalent.ui.auth.fragments.register

import androidx.lifecycle.*
import com.capstone.ditalent.data.repository.auth.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun register(
        name: String,
        email: String,
        role: String,
        noPhone: String,
        password: String
    ): LiveData<RegisterUiState> =
        userRepository.register(name, email, role, noPhone, password)
            .map { result ->
                RegisterUiState(
                    isSuccess = result.keys.first(),
                    isError = !result.keys.first(),
                    messageError = result.values.first()
                )
            }.onStart { emit(RegisterUiState(isLoading = true)) }
            .catch { emit(RegisterUiState(isError = true)) }
            .asLiveData()
}