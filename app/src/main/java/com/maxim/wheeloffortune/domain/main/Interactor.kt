package com.maxim.wheeloffortune.domain.main

interface Interactor {
    suspend fun getItemList(): List<DomainItem>
    suspend fun openItem(id: Int)
    fun closeItem()
}