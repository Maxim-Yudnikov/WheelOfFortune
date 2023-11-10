package com.maxim.wheeloffortune.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RoomDao {
    @Insert
    suspend fun insertWheel(wheel: WheelRoomModel): Long
    @Update
    suspend fun updateWheel(wheel: WheelRoomModel)
    @Query("SELECT * FROM wheels")
    suspend fun getAllWheels(): List<WheelRoomModel>
    @Query("DELETE FROM wheels WHERE id IS :id")
    suspend fun deleteWheel(id: Int)
    @Insert
    suspend fun insertItem(item: ItemRoomModel)
    @Query("SELECT * FROM items WHERE wheelId IS :id")
    suspend fun getAllItemsWithId(id: Int): List<ItemRoomModel>
    @Query("DELETE FROM items WHERE wheelId IS :id")
    suspend fun deleteAllItemsWithId(id: Int)
}