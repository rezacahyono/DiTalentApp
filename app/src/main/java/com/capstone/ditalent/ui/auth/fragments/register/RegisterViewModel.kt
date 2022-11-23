package com.capstone.ditalent.ui.auth.fragments.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.ditalent.data.remote.dto.auth.RequestUser
import com.capstone.ditalent.data.repository.auth.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun register(requestUser: RequestUser): LiveData<RegisterUiState> =
        userRepository.register(requestUser)
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