package com.maxim.wheeloffortune.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maxim.wheeloffortune.data.DataItem

@Entity(tableName = "items")
data class ItemRoomModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val wheelId: Int,
    val name: String,
    val color: Int
) {
    fun mapToData() = DataItem.BaseDataItem(name, color)
}