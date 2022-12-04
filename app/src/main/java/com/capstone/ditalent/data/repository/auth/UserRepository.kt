package com.capstone.ditalent.data.repository.auth

import com.capstone.ditalent.model.User
import com.capstone.ditalent.utils.UiText
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import com.capstone.ditalent.utils.Result

interface UserRepository {

    val currentUser: Flow<FirebaseUser>

    val user: Flow<User?>

    fun loginWithEmailPassword(email: String, password: String): Flow<Result<UiText>>

    fun loginWithGoogle(
        credential: AuthCredential,
        role: String
    ): Flow<Result<UiText>>

    fun register(
        name: String,
        email: String,
        role: String,
        noPhone: String,
        password: String
    ): Flow<Result<UiText>>

    fun logout(): Flow<Result<UiText>>

}