package com.example.cocarelish.domain.mission

import androidx.lifecycle.LiveData
import com.example.cocarelish.domain.room.entity.Mission

interface MissionRepository {
    suspend fun getMissionByTypeString(typeMission: String): LiveData<Mission>
    suspend fun getAllMission(): LiveData<List<Mission>>
    suspend fun insertMission(mission: Mission)
}