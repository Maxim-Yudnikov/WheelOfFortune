package com.maxim.wheeloffortune.domain

interface Interactor {
    suspend fun getItemList(): List<DomainItem>
    fun openItem(id: Int)
    suspend fun rotate(): String
}