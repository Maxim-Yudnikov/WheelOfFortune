package com.maxim.wheeloffortune.presentation.edit

interface EndEditResult {
    fun getMessage(): String
    object Success: EndEditResult {
        override fun getMessage(): String = "success"
    }

    data class Failed(private val message: String): EndEditResult {
        override fun getMessage(): String = message
    }
}