package com.example.cocarelish.data.common.room
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cocarelish.data.mission.local.MissionDAO
import com.example.cocarelish.domain.room.entity.Mission

@Database(entities = arrayOf(Mission::class), version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase()
{
    abstract fun missionDAO(): MissionDAO

}