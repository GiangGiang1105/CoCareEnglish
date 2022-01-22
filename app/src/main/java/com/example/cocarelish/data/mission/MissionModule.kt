package com.example.cocarelish.data.mission

import com.example.cocarelish.data.common.module.NetworkModule
import com.example.cocarelish.data.common.room.AppDataBase
import com.example.cocarelish.data.mission.local.MissionDAO
import com.example.cocarelish.data.mission.repository.MissionRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class MissionModule {

    @Singleton
    @Provides
    fun provideMissionDAO(db: AppDataBase) = db.missionDAO()

    @Singleton
    @Provides
    fun provideMissionRepository(missionDAO: MissionDAO) = MissionRepositoryImpl(missionDAO)
}