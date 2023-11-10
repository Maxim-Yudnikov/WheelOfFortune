package com.maxim.wheeloffortune.domain.edit

interface EditInteractor {
    suspend fun deleteWheel()
    fun createItem()
    suspend fun deleteItem(id: Int)
    fun changeItemName(id: Int, name: String)
    fun changeItemColor(id: Int, colorId: Int)
    suspend fun endEditing(title: String)
    fun cancelEditing()
}