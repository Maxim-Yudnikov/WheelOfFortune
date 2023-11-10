package com.maxim.wheeloffortune.domain.main

import com.maxim.wheeloffortune.data.WheelMainDataSource
import kotlinx.coroutines.delay

class BaseInteractor(
    private val dataSource: WheelMainDataSource
): Interactor {
    override suspend fun getItemList(): List<DomainItem> {
        val list = dataSource.getWheelList().toMutableList()
        list.add(DomainItem.Empty)
        return list
    }

    override suspend fun openItem(id: Int) {
        dataSource.cache(id)
    }

    override suspend fun rotate(): String {
        delay(200)
        return dataSource.getRandomItemName()
    }

    override fun closeItem() {
        dataSource.closeWheel()
    }
}