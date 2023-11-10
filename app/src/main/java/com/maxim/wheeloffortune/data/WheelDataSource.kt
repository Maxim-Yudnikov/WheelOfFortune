package com.maxim.wheeloffortune.data

import com.maxim.wheeloffortune.domain.main.DomainItem

interface WheelDataSource : WheelEditDataSource, WheelMainDataSource

interface WheelMainDataSource {
    suspend fun getWheelList(): List<DomainItem>
    suspend fun cache(id: Int)
    suspend fun getRandomItemName(): String
    fun closeWheel()
}

interface WheelEditDataSource {
    suspend fun deleteWheel()
    fun createNewItem()
    fun deleteItem(id: Int)
    fun getList(): List<DomainItem.BaseDomainItem>
    fun changeItemName(id:Int, name: String)
    fun changeItemColor(id: Int, color: Int)
    suspend fun endEditing(title: String)
    fun cancelEditing()
}