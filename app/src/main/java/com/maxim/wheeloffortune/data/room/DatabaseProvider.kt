package com.maxim.wheeloffortune.data.room

import android.content.Context
import androidx.room.Room

class DatabaseProvider(private val context: Context) {
    private val database by lazy {
        return@lazy Room.databaseBuilder(context, RoomDatabase::class.java, "database").build()
    }
    fun provideDatabase() = database
}