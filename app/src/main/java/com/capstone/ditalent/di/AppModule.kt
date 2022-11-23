package com.capstone.ditalent.di

import com.capstone.ditalent.data.local.preferences.UserPreferences
import com.capstone.ditalent.data.remote.data_source.UserRemoteDataSource
import com.capstone.ditalent.data.repository.auth.UserRepository
import com.capstone.ditalent.data.repository.auth.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideUserRepository(
        userRemoteDataSource: UserRemoteDataSource,
        userPreferences: UserPreferences
    ): UserRepository =
        UserRepositoryImpl(
            userRemoteDataSource,
            userPreferences
        )
}