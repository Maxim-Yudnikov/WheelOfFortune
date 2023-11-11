package com.maxim.wheeloffortune.presentation.edit

interface UiValidator {
    fun getMessage(): String
    fun isValid(value: String): Boolean
}