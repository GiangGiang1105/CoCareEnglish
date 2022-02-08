package com.example.cocarelish.base

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppHiltModule {

    @Singleton
    @Provides
    fun provideFireBaseAuth() = FirebaseAuth.getInstance()
}