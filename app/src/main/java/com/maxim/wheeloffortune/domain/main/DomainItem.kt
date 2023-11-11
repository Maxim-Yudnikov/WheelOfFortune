package com.maxim.wheeloffortune.domain.main

import com.maxim.wheeloffortune.presentation.main.UiItem

interface DomainItem {
    fun mapToUi(): UiItem
    data class BaseDomainWheel(private val id: Int, private val title: String, private val list: List<BaseDomainItem>):
        DomainItem {
        override fun mapToUi(): UiItem {
            return UiItem.BaseUiWheel(id, title, list.map { it.mapToUi() as UiItem.BaseUiItem })
        }
    }

    data class FailedDomainItem(private val message: String): DomainItem {
        override fun mapToUi(): UiItem {
            return UiItem.FailedUiItem(message)
        }
    }

    data class BaseDomainItem(private val name: String, private val color: Int) : DomainItem {
        override fun mapToUi(): UiItem {
            return UiItem.BaseUiItem(name, color)
        }
    }

    object Empty: DomainItem {
        override fun mapToUi(): UiItem {
            return UiItem.Empty
        }
    }
}