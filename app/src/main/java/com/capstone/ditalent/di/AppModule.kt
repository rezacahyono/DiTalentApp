package com.capstone.ditalent.di

import com.capstone.ditalent.data.repository.auth.UserRepository
import com.capstone.ditalent.data.repository.auth.UserRepositoryImpl
import com.capstone.ditalent.data.repository.talent.TalentRepository
import com.capstone.ditalent.data.repository.talent.TalentRepositoryImpl
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        firebaseAuth: FirebaseAuth,
        googleSignInClient: GoogleSignInClient,
        firestore: FirebaseFirestore
    ): UserRepository = UserRepositoryImpl(firebaseAuth,googleSignInClient, firestore)

//    @Provides
//    @Singleton
//    fun provideUmkmRepository(
//        firestore: FirebaseFirestore
//    ): UmkmRepository = UmkmRepositoryImpl(firestore)

    @Provides
    @Singleton
    fun provideTalentRepository(
        firestore: FirebaseFirestore
    ): TalentRepository = TalentRepositoryImpl(firestore)

}