package com.maxim.wheeloffortune.domain.edit

import com.maxim.wheeloffortune.data.WheelEditDataSource
import com.maxim.wheeloffortune.domain.FailureHandler
import com.maxim.wheeloffortune.domain.main.DomainItem

class BaseEditInteractor(
    private val dataSource: WheelEditDataSource,
    private val failureHandler: FailureHandler
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

    override fun getList(): List<DomainItem> {
        return try {
            dataSource.getList()
        } catch (e: Exception) {
            return listOf(DomainItem.FailedDomainItem(failureHandler.handle(e).getMessage()))
        }
    }

    override fun changeItemName(id: Int, name: String) {
        dataSource.changeItemName(id, name)
    }

    override fun changeItemColor(id: Int, colorId: Int) {
        dataSource.changeItemColor(id, colorId)
    }

    override suspend fun endEditing(title: String): String {
        return try {
            dataSource.endEditing(title)
            "success"
        } catch (e: Exception) {
            failureHandler.handle(e).getMessage()
        }
    }

    override fun cancelEditing() {
        dataSource.cancelEditing()
    }
}