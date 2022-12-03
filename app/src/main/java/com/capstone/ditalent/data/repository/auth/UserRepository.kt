package com.capstone.ditalent.data.repository.auth

import com.capstone.ditalent.model.User
import com.capstone.ditalent.utils.UiText
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    val firebaseUser: Flow<FirebaseUser>

    fun getUser(userId: String): Flow<User>

    fun loginWithEmailPassword(email: String, password: String): Flow<Map<Boolean, UiText>>

    fun loginWithGoogle(credential: AuthCredential,role: String): Flow<Map<Boolean, UiText>>

    fun register(
        name: String,
        email: String,
        role: String,
        noPhone: String,
        password: String
    ): Flow<Map<Boolean, UiText>>

    fun logout(): Flow<Map<Boolean, UiText>>

}