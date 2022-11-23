package com.capstone.ditalent.data.repository.auth

import com.capstone.ditalent.data.remote.dto.auth.RequestUser
import com.capstone.ditalent.model.User
import com.capstone.ditalent.utils.UiText
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    val userPref: Flow<User>

    suspend fun setUserpref(user: User)

    fun login(email: String, password: String): Flow<Map<Boolean, UiText>>

    fun register(requestUser: RequestUser): Flow<Map<Boolean, UiText>>

}