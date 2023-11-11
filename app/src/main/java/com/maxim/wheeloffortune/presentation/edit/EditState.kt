package com.maxim.wheeloffortune.presentation.edit

import com.google.android.material.textfield.TextInputLayout

interface EditState {
    fun apply(textInputLayout: TextInputLayout)
    data class TitleError(private val message: String): EditState {
        override fun apply(textInputLayout: TextInputLayout) {
            textInputLayout.isErrorEnabled = true
            textInputLayout.error = message
        }
    }
}