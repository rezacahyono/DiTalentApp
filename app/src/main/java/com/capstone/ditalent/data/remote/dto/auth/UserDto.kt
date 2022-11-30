package com.capstone.ditalent.data.remote.dto.auth

import com.google.gson.annotations.SerializedName

data class UserDto(

    @SerializedName("role")
    val role: String,

    @SerializedName("address")
    val address: String? = null,

    @SerializedName("updated_at")
    val updatedAt: String? = null,

    @SerializedName("talent")
    val talentDto: TalentDto? = null,

    @SerializedName("created_at")
    val createdAt: String? = null,

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("avatar")
    val avatar: String? = null,

    @SerializedName("no_phone")
    val noPhone: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("social_media")
    val socialMediaDto: List<SocialMediaDto>? = emptyList()
)