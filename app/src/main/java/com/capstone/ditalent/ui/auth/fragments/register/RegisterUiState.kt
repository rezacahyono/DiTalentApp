package com.capstone.ditalent.ui.auth.fragments.register

import com.capstone.ditalent.utils.UiText

data class RegisterUiState(
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val messageError: UiText? = null
)
