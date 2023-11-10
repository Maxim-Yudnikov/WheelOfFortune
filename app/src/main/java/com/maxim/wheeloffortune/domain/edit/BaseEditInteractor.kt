package com.maxim.wheeloffortune.domain.edit

import com.maxim.wheeloffortune.data.WheelEditDataSource

class BaseEditInteractor(
    private val dataSource: WheelEditDataSource
): EditInteractor {
    override suspend fun deleteWheel() {
        dataSource.deleteWheel()
    }

    override fun createItem() {
        dataSource.createNewItem()
    }

    override suspend fun deleteItem(id: Int) {
        dataSource.deleteItem(id)
    }

    override fun changeItemName(id: Int, name: String) {
        dataSource.changeItemName(id, name)
    }

    override fun changeItemColor(id: Int, colorId: Int) {
        dataSource.changeItemColor(id, colorId)
    }

    override suspend fun endEditing(title: String) {
        dataSource.endEditing(title)
    }

    override fun cancelEditing() {
        dataSource.cancelEditing()
    }
}