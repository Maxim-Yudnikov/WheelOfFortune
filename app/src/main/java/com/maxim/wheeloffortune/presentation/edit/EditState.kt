package com.maxim.wheeloffortune.presentation.edit

import android.view.View
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout

interface EditState {
    fun apply(textInputLayout: TextInputLayout, errorTextView: TextView)
    data class TitleError(private val message: String): EditState {
        override fun apply(textInputLayout: TextInputLayout, errorTextView: TextView) {
            errorTextView.visibility = View.GONE
            textInputLayout.isErrorEnabled = true
            textInputLayout.error = message
        }
    }

    data class ItemListError(private val message: String): EditState {
        override fun apply(textInputLayout: TextInputLayout, errorTextView: TextView) {
            errorTextView.visibility = View.VISIBLE
            errorTextView.text = message
        }
    }

    object Success: EditState {
        override fun apply(textInputLayout: TextInputLayout, errorTextView: TextView) {
            textInputLayout.error = ""
            textInputLayout.isErrorEnabled = false
            errorTextView.visibility = View.GONE
        }

    }
}