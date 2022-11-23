package com.capstone.ditalent.data.remote.service

import com.capstone.ditalent.data.remote.dto.auth.RequestUser
import com.capstone.ditalent.data.remote.dto.auth.ResponseUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface DitalentApi {

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<ResponseUser>

    @POST("register")
    suspend fun register(
        @Body requestUser: RequestUser
    ): Response<ResponseUser>

}