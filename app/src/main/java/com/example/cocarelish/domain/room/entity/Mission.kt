package com.example.cocarelish.domain.room.entity

import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Mission.tableName)
data class Mission(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null,
    var missionType: String,
    var count: Int,
    var max: Int,
    var missionContent: String
) {
    companion object {
        const val tableName = "mission"

        val listMissionInit = listOf<Mission>(
            Mission(
                missionType = MissionType.EssayDone.name,
                count = 0,
                max = 10,
                missionContent = "Write your 5 essays today"
            ),
            Mission(
                missionType = MissionType.Speaking.name,
                count = 0,
                max = 10,
                missionContent = "Speak 10 with topic Family"
            )
        )
    }
}

enum class MissionType {
    EssayDone, Vocabulary, Speaking
}

object MissionItemDiffCallback : DiffUtil.ItemCallback<Mission>() {
    override fun areItemsTheSame(oldItem: Mission, newItem: Mission) =
        oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: Mission, newItem: Mission) =
        oldItem == newItem
}
