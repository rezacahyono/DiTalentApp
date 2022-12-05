package com.capstone.ditalent.ui.umkm.fragments.profile

import com.capstone.ditalent.utils.UiText

data class ProfileUmkmUiState(
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val message: UiText? = null,
)
