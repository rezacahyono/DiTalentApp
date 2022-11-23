package com.capstone.ditalent.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val token: String,
    val name: String,
    val email: String,
    val role: Role,
    val noPhone: String,
    val avatar: String,
    val address: String,
    val isLogin: Boolean = false
)
