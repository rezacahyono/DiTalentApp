package com.capstone.ditalent.data.remote.data_source

import com.capstone.ditalent.data.remote.dto.auth.RequestUser
import com.capstone.ditalent.data.remote.service.DitalentApi
import com.capstone.ditalent.utils.handleApi
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val ditalentApi: DitalentApi,
) {
    suspend fun login(
        email: String,
        password: String
    ) = handleApi { ditalentApi.login(email = email, password = password) }

    suspend fun register(
        requestUser: RequestUser
    ) = handleApi { ditalentApi.register(requestUser = requestUser) }


    suspend fun getMe(token: String) = handleApi { ditalentApi.getMe(token = token) }
}