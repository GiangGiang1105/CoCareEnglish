package com.example.cocarelish.data.essay

import com.example.cocarelish.data.common.module.NetworkModule
import com.example.cocarelish.data.essay.remote.api.EssayApi
import com.example.cocarelish.data.essay.repository.EssayRepositoryImpl
import com.example.cocarelish.domain.essay.EssayRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class EssayModule {

    @Singleton
    @Provides
    fun provideEssayAPI(retrofit: Retrofit): EssayApi {

        return retrofit.create(EssayApi::class.java)
    }

    @Singleton
    @Provides
    fun provideEssayRepository(essayApi: EssayApi) : EssayRepository {
        return EssayRepositoryImpl(essayApi)
    }
}
