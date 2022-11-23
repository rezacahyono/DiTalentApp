package com.capstone.ditalent.data.remote.dto.auth

import com.google.gson.annotations.SerializedName

data class ResponseUser(

    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("data")
    val data: UserDto,

    @SerializedName("message")
    val message: String
)