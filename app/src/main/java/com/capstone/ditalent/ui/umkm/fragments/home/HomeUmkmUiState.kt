package com.capstone.ditalent.ui.umkm.fragments.home

import com.capstone.ditalent.model.Talent
import com.capstone.ditalent.model.User

data class HomeUmkmUiState(
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val talents: List<User> = emptyList()
)