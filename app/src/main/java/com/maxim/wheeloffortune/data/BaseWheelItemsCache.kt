package com.maxim.wheeloffortune.data

class BaseWheelItemsCache: WheelItemsCache {
    private var cache = mutableListOf<DataItem.BaseDataItem>()
    override fun cache(list: List<DataItem.BaseDataItem>) {
        cache.addAll(list)
    }

    override fun createItem(item: DataItem.BaseDataItem) {
        cache.add(item)
    }

    override fun changeItemName(id: Int, newName: String) {
        cache[id] = cache[id].rename(newName)
    }

    override fun changeItemColor(id: Int, newColor: Int) {
        cache[id] = cache[id].changeColor(newColor)
    }

    override fun deleteItem(id: Int) {
        cache.removeAt(id)
    }

    override fun getItemList(): List<DataItem.BaseDataItem> {
        return cache
    }

    override fun clear() {
        cache.clear()
    }
}