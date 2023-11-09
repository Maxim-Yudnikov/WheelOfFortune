package com.maxim.wheeloffortune.domain.main

import com.maxim.wheeloffortune.data.WheelDataSource
import kotlinx.coroutines.delay

class BaseInteractor(
    private val dataSource: WheelDataSource
): Interactor {
    override suspend fun getItemList(): List<DomainItem> {
        val list = dataSource.getItemList().toMutableList()
        list.add(DomainItem.Empty)
        return list
    }

    override fun openItem(id: Int) {
        dataSource.cache(id)
    }

    override suspend fun rotate(): String {
        delay(2000)
        return dataSource.getRandomItemName()
    }
}