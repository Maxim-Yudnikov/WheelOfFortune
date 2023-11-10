package com.maxim.wheeloffortune.data

interface WheelItemsCache {
    fun cache(list: List<DataItem.BaseDataItem>)
    fun createItem(item: DataItem.BaseDataItem)
    fun changeItemName(id: Int, newName: String)
    fun changeItemColor(id: Int, newColor: Int)
    fun deleteItem(id: Int)
    fun getItemList(): List<DataItem.BaseDataItem>
    fun clear()
}