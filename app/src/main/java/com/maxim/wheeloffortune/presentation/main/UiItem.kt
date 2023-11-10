package com.maxim.wheeloffortune.presentation.main

import com.maxim.wheeloffortune.data.WheelDataItem

interface UiItem {
    data class BaseUiWheel(
        private val id: Int,
        private val title: String,
        private val itemList: List<BaseUiItem>
    ) : UiItem

    data class BaseUiItem(private val name: String, private val color: Int) : UiItem
    object Empty : UiItem
}