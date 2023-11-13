package com.maxim.wheeloffortune.presentation.main

import android.content.res.Resources
import android.widget.Button
import android.widget.TextView
import com.maxim.wheeloffortune.R

abstract class State {
    abstract fun apply(textView: TextView, actionButton: Button, resourcesManager: Resources)

    object Init : State() {
        override fun apply(textView: TextView, actionButton: Button, resourcesManager: Resources) {
            textView.text = resourcesManager.getString(R.string.init_text)
        }
    }

    object Rotating : State() {
        override fun apply(textView: TextView, actionButton: Button, resourcesManager: Resources) {
            textView.text = resourcesManager.getString(R.string.rotation)
            actionButton.isEnabled = false
        }
    }

    data class Done(private val itemName: String) : State() {
        override fun apply(textView: TextView, actionButton: Button, resourcesManager: Resources) {
            textView.text = itemName
            actionButton.isEnabled = true
        }
    }
}