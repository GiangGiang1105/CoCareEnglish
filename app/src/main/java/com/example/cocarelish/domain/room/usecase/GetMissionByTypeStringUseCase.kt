package com.example.cocarelish.domain.room.usecase

import androidx.lifecycle.LiveData
import com.example.cocarelish.data.mission.repository.MissionRepositoryImpl
import com.example.cocarelish.domain.room.entity.Mission
import javax.inject.Inject

class MissionUseCase @Inject constructor(private val missionRepositoryImpl: MissionRepositoryImpl){
    suspend fun getMissionByTypeString(typeMission: String): LiveData<Mission> {
        return missionRepositoryImpl.getMissionByTypeString(typeMission)
    }

    suspend fun getAllMission(): LiveData<List<Mission>>{
        return missionRepositoryImpl.getAllMission()
    }

    suspend fun insertMission(mission: Mission){
        missionRepositoryImpl.insertMission(mission)
    }
}