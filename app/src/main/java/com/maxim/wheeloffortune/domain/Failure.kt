package com.maxim.wheeloffortune.domain

interface Failure {
    fun getMessage(): String
}

class EmptyItemListError: Failure {
    override fun getMessage() = "Item list must not be empty"
}

class EmptyItemNameError(private val id: Int): Failure {
    override fun getMessage(): String = "Item name must not be empty. Empty item name at position ${id+1}"
}

class UnknownError: Failure {
    override fun getMessage() = "Unknown error"
}