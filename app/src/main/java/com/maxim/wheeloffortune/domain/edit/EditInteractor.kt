package com.maxim.wheeloffortune.domain.edit

import com.maxim.wheeloffortune.domain.main.DomainItem

interface EditInteractor {
    suspend fun deleteWheel()
    fun createItem()
    suspend fun deleteItem(id: Int)
    fun getList(): List<DomainItem>
    fun changeItemName(id: Int, name: String)
    fun changeItemColor(id: Int, colorId: Int)
    suspend fun endEditing(title: String, onSuccess: () -> Unit, onFailed: (message: String, position: Int) -> Unit)
    fun cancelEditing()
}