package com.capstone.ditalent.data.remote.dto.auth

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("role")
    val role: String,

    @SerializedName("address")
    val address: String? = null,

    @SerializedName("updated_at")
    val updatedAt: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("avatar")
    val avatar: String? = null,

    @SerializedName("no_phone")
    val noPhone: String,

    @SerializedName("email")
    val email: String
)
