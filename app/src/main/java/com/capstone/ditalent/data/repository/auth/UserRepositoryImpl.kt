package com.capstone.ditalent.data.repository.auth

import android.util.Log
import com.capstone.ditalent.data.local.preferences.UserPreferences
import com.capstone.ditalent.data.remote.data_source.UserRemoteDataSource
import com.capstone.ditalent.data.remote.dto.auth.RequestUser
import com.capstone.ditalent.data.toUser
import com.capstone.ditalent.model.User
import com.capstone.ditalent.utils.ApiResult
import com.capstone.ditalent.utils.UiText
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userPreferences: UserPreferences,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : UserRepository {

    override val userPref: Flow<User>
        get() = userPreferences.userPref.flowOn(ioDispatcher)

    override suspend fun setUserpref(user: User) {
        withContext(ioDispatcher){
            userPreferences.setUserPref(user)
        }
    }

    override fun login(email: String, password: String): Flow<Map<Boolean, UiText>> = flow {
        when (val source = userRemoteDataSource.login(email, password)) {
            is ApiResult.ApiSuccess -> {
                val message = source.data.message
                val result = mapOf(true to UiText.DynamicString(message))
                emit(result)
                userPreferences.setUserPref(source.data.toUser().copy(isLogin = true))
            }
            is ApiResult.ApiError -> {
                val result = mapOf(false to source.uiText)
                emit(result)
            }
        }
    }.flowOn(ioDispatcher)

    override fun register(requestUser: RequestUser): Flow<Map<Boolean, UiText>> = flow {
        when (val source = userRemoteDataSource.register(requestUser)) {
            is ApiResult.ApiSuccess -> {
                val message = source.data.message
                val result = mapOf(true to UiText.DynamicString(message))
                emit(result)
            }
            is ApiResult.ApiError -> {
                Log.d("TAG", "register: ${source.uiText}")
                val result = mapOf(false to source.uiText)
                emit(result)
            }
        }
    }.flowOn(ioDispatcher)


}