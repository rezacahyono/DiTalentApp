package com.capstone.ditalent.ui.auth.fragments.login

import com.capstone.ditalent.model.User
import com.capstone.ditalent.utils.UiText
import com.google.firebase.auth.FirebaseUser

data class LoginUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: UiText? = null,
    val firebaseUser: FirebaseUser? = null,
    val user: User? = null
)
