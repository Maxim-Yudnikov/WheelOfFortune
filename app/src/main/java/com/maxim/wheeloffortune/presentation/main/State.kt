package com.maxim.wheeloffortune.presentation.main

abstract class State {

    object Rotating: State()
    data class Done(private val itemName: String): State()
}