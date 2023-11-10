package com.maxim.wheeloffortune.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WheelRoomModel::class, ItemRoomModel::class], version = 1)
abstract class RoomDatabase: RoomDatabase() {
    abstract fun dao(): RoomDao
}