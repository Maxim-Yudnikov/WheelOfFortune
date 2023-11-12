package com.maxim.wheeloffortune.domain.edit

import com.maxim.wheeloffortune.domain.main.DomainItem
import com.maxim.wheeloffortune.presentation.edit.EndEditResult

interface EditInteractor {
    suspend fun deleteWheel()
    fun createItem()
    suspend fun deleteItem(id: Int)
    fun getList(): List<DomainItem>
    fun changeItemName(id: Int, name: String)
    fun changeItemColor(id: Int, colorId: Int)
    suspend fun endEditing(title: String): EndEditResult
    fun cancelEditing()
}