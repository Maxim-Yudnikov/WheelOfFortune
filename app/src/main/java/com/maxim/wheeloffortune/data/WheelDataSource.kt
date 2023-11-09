package com.maxim.wheeloffortune.data

import com.maxim.wheeloffortune.domain.main.DomainItem

interface WheelDataSource {
    suspend fun getItemList(): List<DomainItem>
    fun cache(id: Int)
    fun getRandomItemName(): String
}