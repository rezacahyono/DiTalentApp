package com.capstone.ditalent.model

import kotlinx.serialization.Serializable

@Serializable
data class SocialMedia(
    val id: Int,
    val userId: Int,
    val name: String,
    val username: String,
    val followers: String,
    val insight: String,
    val url: String
)