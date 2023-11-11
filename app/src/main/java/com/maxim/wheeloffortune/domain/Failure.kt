package com.maxim.wheeloffortune.domain

interface Failure {
    fun getMessage(): String
}

class EmptyItemListError: Failure {
    override fun getMessage() = "Empty item list"
}

class UnknownError: Failure {
    override fun getMessage() = "Unknown error"
}