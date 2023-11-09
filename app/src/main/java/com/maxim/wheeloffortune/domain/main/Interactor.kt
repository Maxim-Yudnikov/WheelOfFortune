package com.maxim.wheeloffortune.domain.main

interface Interactor {
    suspend fun getItemList(): List<DomainItem>
    fun openItem(id: Int)
    suspend fun rotate(): String
}