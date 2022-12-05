package com.capstone.ditalent.ui.talent.fragments.profile


import com.capstone.ditalent.utils.UiText

data class ProfileTalentUiState(
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val message: UiText? = null,
)
