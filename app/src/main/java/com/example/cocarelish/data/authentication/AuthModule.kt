package com.example.cocarelish.data.authentication

import com.example.cocarelish.data.authentication.remote.api.AuthApi
import com.example.cocarelish.data.authentication.repository.AuthRepositoryImpl
import com.example.cocarelish.data.common.module.NetworkModule
import com.example.cocarelish.domain.auth.AuthRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class AuthModule {
    @Singleton
    @Provides
    fun provideAuthenticationAPI(retrofit: Retrofit):AuthApi{
        return retrofit.create(AuthApi::class.java)
    }

    @Singleton
    @Provides
    fun provideLoginRepository(authApi: AuthApi, firebaseFirestore: FirebaseFirestore) : AuthRepository {
        return AuthRepositoryImpl(authApi, firebaseFirestore)
    }
}