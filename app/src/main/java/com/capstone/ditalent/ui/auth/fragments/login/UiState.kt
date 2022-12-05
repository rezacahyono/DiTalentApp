package com.capstone.ditalent.ui.auth.fragments.login

import com.capstone.ditalent.utils.UiText

data class UiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val message: UiText? = null,
)
