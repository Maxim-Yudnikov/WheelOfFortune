package com.maxim.wheeloffortune.domain.edit

import com.maxim.wheeloffortune.data.WheelEditDataSource
import com.maxim.wheeloffortune.domain.EmptyItemNameException
import com.maxim.wheeloffortune.domain.FailureHandler
import com.maxim.wheeloffortune.domain.main.DomainItem

class BaseEditInteractor(
    private val dataSource: WheelEditDataSource,
    private val failureHandler: FailureHandler
) : EditInteractor {
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
            dataSource.getItemList()
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

    override suspend fun endEditing(
        title: String,
        onSuccess: () -> Unit,
        onFailed: (message: String, position: Int) -> Unit
    ) {
        try {
            dataSource.endEditing(title)
            onSuccess.invoke()
        } catch (e: Exception) {
            val position = if (e is EmptyItemNameException) (e).message.toInt() else -1
            onFailed.invoke(failureHandler.handle(e).getMessage(), position)
        }
    }

    override fun cancelEditing() {
        dataSource.cancelEditing()
    }
}