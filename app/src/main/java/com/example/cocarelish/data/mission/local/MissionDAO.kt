package com.example.cocarelish.data.mission.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RawQuery
import com.example.cocarelish.domain.room.entity.Mission
import com.example.cocarelish.utils.RoomExtension

@Dao
interface MissionDAO {
    @Query("SELECT * FROM "+ RoomExtension.Mission +" where missionType = :typeMission")
    fun getMissionByTypeString(typeMission: String) : LiveData<Mission>

    @Query("select * from ${RoomExtension.Mission}" )
    fun getAllMission() : LiveData<List<Mission>>

    @Insert
    fun insertMission(mission: Mission)

    @Insert
    fun insertAllMission(liMission: List<Mission>)
}
