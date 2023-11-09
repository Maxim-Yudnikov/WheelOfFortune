package com.maxim.wheeloffortune.presentation

import com.maxim.wheeloffortune.domain.WheelItem

interface UiItem {
    data class BaseUiItem(private val title: String, private val itemList: List<WheelItem>): UiItem
    object Empty: UiItem
}