package com.maxim.wheeloffortune.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maxim.wheeloffortune.data.DataItem

@Entity(tableName = "wheels")
data class WheelRoomModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val title: String,
) {
}