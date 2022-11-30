package com.capstone.ditalent.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val token: String,
    val username: String,
    val email: String,
    val role: Role,
    val noPhone: String,
    val avatar: String,
    val address: String,
    val talent: Talent? = null,
    val umkm: Umkm? = null,
    val socialMedias: List<SocialMedia> = emptyList(),
    val isLogin: Boolean = false
)
