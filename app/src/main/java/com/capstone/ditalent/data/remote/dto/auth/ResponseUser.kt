package com.capstone.ditalent.data.remote.dto.auth

import com.google.gson.annotations.SerializedName

data class ResponseUser(

    @SerializedName("data")
    val userDto: UserDto,

    @SerializedName("message")
    val message: String,

    @SerializedName("access_token")
    val token: String? = null
)