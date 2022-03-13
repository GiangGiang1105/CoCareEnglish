package com.example.cocarelish.data.order

import com.example.cocarelish.data.common.module.NetworkModule
import com.example.cocarelish.data.order.repository.OrderRepositoryImpl
import com.example.cocarelish.domain.essay.usecase.EssayOfSystemUseCase
import com.example.cocarelish.domain.order.OrderRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class OrderModule {


    @Singleton
    @Provides
    fun provideOrderRepository(fireStore: FirebaseFirestore, essayOfSystemUseCase: EssayOfSystemUseCase) : OrderRepository {
        return OrderRepositoryImpl(fireStore, essayOfSystemUseCase)
    }
}