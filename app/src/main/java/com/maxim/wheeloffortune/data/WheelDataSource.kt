package com.maxim.wheeloffortune.data

import com.maxim.wheeloffortune.domain.main.DomainItem

interface WheelDataSource : WheelEditDataSource, WheelMainDataSource

interface WheelMainDataSource {
    suspend fun getItemList(): List<DomainItem>
    fun cache(id: Int)
    fun getRandomItemName(): String
}

interface WheelEditDataSource {
    suspend fun deleteWheel()
    fun createNewItem()
    fun deleteItem(id: Int)
    fun changeItemName(id:Int, name: String)
    fun changeItemColor(id: Int, color: Int)
    fun endEditing(title: String)
}