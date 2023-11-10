package com.maxim.wheeloffortune.data

import com.maxim.wheeloffortune.domain.main.DomainItem

interface DataItem {
    fun mapToDomain(): DomainItem
    //todo fix public
    data class BaseDataItem(val name: String, val color: Int): DataItem {
        override fun mapToDomain(): DomainItem {
            return DomainItem.BaseDomainItem(name, color)
        }

        fun rename(newName: String): BaseDataItem {
            return BaseDataItem(newName, color)
        }

        fun changeColor(newColor: Int): BaseDataItem {
            return BaseDataItem(name, newColor)
        }
    }

    data class BaseWheelItem(
        private val id: Int,
        private val title: String,
        private val itemList: List<BaseDataItem>): DataItem {
        override fun mapToDomain(): DomainItem {
            return DomainItem.BaseDomainWheel(id, title, itemList.map { it.mapToDomain() as DomainItem.BaseDomainItem })
        }
    }
}