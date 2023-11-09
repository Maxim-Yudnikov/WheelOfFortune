package com.maxim.wheeloffortune.presentation

abstract class State {

    object Rotating: State()
    data class Done(private val itemName: String): State()
}