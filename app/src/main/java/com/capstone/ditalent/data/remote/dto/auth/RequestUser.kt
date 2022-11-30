package com.capstone.ditalent.data.remote.dto.auth

import com.google.gson.annotations.SerializedName
import java.io.File

data class RequestUser(

    @SerializedName("username")
    val username: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("role")
    val role: String,

    @SerializedName("no_phone")
    val noPhone: String,

    @SerializedName("avatar")
    val avatar: File? = null,

    @SerializedName("address")
    val address: String? = null,

    @SerializedName("password")
    val password: String
)
