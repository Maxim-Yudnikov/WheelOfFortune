package com.maxim.wheeloffortune.presentation.main

import android.widget.Button
import android.widget.TextView

abstract class State {
    abstract fun apply(textView: TextView, actionButton: Button)

    object Rotating: State() {
        override fun apply(textView: TextView, actionButton: Button) {
            textView.text = "Rotating..."
            actionButton.isEnabled = false
        }
    }

    data class Done(private val itemName: String): State() {
        override fun apply(textView: TextView, actionButton: Button) {
            textView.text = itemName
            actionButton.isEnabled = true
        }
    }
}