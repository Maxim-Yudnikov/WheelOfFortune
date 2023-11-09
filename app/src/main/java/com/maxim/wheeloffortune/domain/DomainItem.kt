package com.maxim.wheeloffortune.domain

import com.maxim.wheeloffortune.presentation.UiItem

interface DomainItem {
    fun mapToUi(): UiItem
    data class BaseDomainItem(private val title: String, private val list: List<WheelItem>): DomainItem {
        override fun mapToUi(): UiItem {
            return UiItem.BaseUiItem(title, list)
        }
    }

    object Empty: DomainItem {
        override fun mapToUi(): UiItem {
            return UiItem.Empty
        }
    }
}