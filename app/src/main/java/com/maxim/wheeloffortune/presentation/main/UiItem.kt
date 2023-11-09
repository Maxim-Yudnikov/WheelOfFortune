package com.maxim.wheeloffortune.presentation.main

import com.maxim.wheeloffortune.domain.main.WheelItem

interface UiItem {
    data class BaseUiItem(private val title: String, private val itemList: List<WheelItem>): UiItem
    object Empty: UiItem
}