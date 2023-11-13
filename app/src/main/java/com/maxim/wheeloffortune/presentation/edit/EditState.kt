package com.maxim.wheeloffortune.presentation.edit

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout

abstract class EditState {
    open fun apply(
        textInputLayout: TextInputLayout,
        errorTextView: TextView,
        recyclerView: RecyclerView
    ) {
        apply(textInputLayout, errorTextView)
    }

    open fun apply(textInputLayout: TextInputLayout, errorTextView: TextView) {}

    data class TitleError(private val message: String) : EditState() {
        override fun apply(textInputLayout: TextInputLayout, errorTextView: TextView) {
            errorTextView.visibility = View.GONE
            textInputLayout.isErrorEnabled = true
            textInputLayout.error = message
        }
    }

    data class ItemListError(private val message: String, private val position: Int) : EditState() {
        override fun apply(
            textInputLayout: TextInputLayout,
            errorTextView: TextView,
            recyclerView: RecyclerView
        ) {
            errorTextView.visibility = View.VISIBLE
            errorTextView.text = message
            if (position != -1)
                recyclerView.scrollToPosition(position)
        }
    }

    object Success : EditState() {
        override fun apply(textInputLayout: TextInputLayout, errorTextView: TextView) {
            textInputLayout.error = ""
            textInputLayout.isErrorEnabled = false
            errorTextView.visibility = View.GONE
        }

    }
}