package com.example.cocarelish.data.mission.repository

import androidx.lifecycle.LiveData
import com.example.cocarelish.data.mission.local.MissionDAO
import com.example.cocarelish.domain.mission.MissionRepository
import com.example.cocarelish.domain.room.entity.Mission
import javax.inject.Inject

class MissionRepositoryImpl @Inject constructor(private val missionDAO: MissionDAO) :
    MissionRepository {

    override suspend fun getMissionByTypeString(typeMission: String): LiveData<Mission> =
        missionDAO.getMissionByTypeString(typeMission)

    override suspend fun getAllMission(): LiveData<List<Mission>> = missionDAO.getAllMission()

    override suspend fun insertMission(mission: Mission) {
        missionDAO.insertMission(mission)
    }
}