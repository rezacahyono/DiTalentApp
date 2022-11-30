package com.capstone.ditalent.data.remote.service

import com.capstone.ditalent.data.remote.dto.auth.RequestUser
import com.capstone.ditalent.data.remote.dto.auth.ResponseUser
import com.capstone.ditalent.utils.Constant.ACCEPT
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface DitalentApi {

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<ResponseUser>

    @POST("auth/register")
    suspend fun register(
        @Header("Accept") accept: String = ACCEPT,
        @Body requestUser: RequestUser
    ): Response<ResponseUser>

    @GET("auth/me")
    suspend fun getMe(
        @Header("Accept") accept: String = ACCEPT,
        @Header("Authorization") token: String
    ): Response<ResponseUser>

}