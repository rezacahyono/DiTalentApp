package com.capstone.ditalent.ui.auth.fragments.login

import com.capstone.ditalent.utils.UiText

data class LoginUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: UiText? = null
)
