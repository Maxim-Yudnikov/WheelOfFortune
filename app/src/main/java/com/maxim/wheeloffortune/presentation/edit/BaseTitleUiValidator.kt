package com.maxim.wheeloffortune.presentation.edit

class BaseTitleUiValidator(
    private val minLength: Int
): UiValidator {
    private var message: String = ""
    override fun getMessage(): String = message

    override fun isValid(value: String): Boolean {
        if(value.isEmpty()) {
            message = "Title must not be empty"
            return false
        }
        if(value.length < minLength) {
            message = "Title length must be at least $minLength"
            return false
        }

        return true
    }
}