package com.capstone.ditalent.ui.talent.fragments.profile

import com.capstone.ditalent.model.User
import com.capstone.ditalent.utils.UiText
import com.google.firebase.auth.FirebaseUser

data class ProfileTalentUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: UiText? = null,
    val user: User? = null,
    val firebaseUser: FirebaseUser? = null
)
