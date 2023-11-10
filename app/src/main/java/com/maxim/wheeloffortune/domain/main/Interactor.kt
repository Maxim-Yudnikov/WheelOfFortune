package com.maxim.wheeloffortune.domain.main

interface Interactor {
    suspend fun getItemList(): List<DomainItem>
    suspend fun openItem(id: Int)
    suspend fun rotate(): String
    fun closeItem()
}