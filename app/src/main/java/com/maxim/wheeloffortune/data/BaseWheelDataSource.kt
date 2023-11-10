package com.maxim.wheeloffortune.data

import com.maxim.wheeloffortune.data.room.ItemRoomModel
import com.maxim.wheeloffortune.data.room.RoomDao
import com.maxim.wheeloffortune.data.room.WheelRoomModel
import com.maxim.wheeloffortune.domain.main.DomainItem

class BaseWheelDataSource(
    private val dao: RoomDao,
    private val wheelCache: WheelCache,
    private val wheelItemsCache: WheelItemsCache
) : WheelDataSource {
    override suspend fun deleteWheel() {
        dao.deleteWheel(wheelCache.getCachedId())
        wheelCache.clear()
    }

    override fun createNewItem() {
        val item = DataItem.BaseDataItem("Title", 0)
        wheelItemsCache.createItem(item)
    }

    override fun deleteItem(id: Int) {
        wheelItemsCache.deleteItem(id)
    }

    override fun changeItemName(id: Int, name: String) {
        wheelItemsCache.changeItemName(id, name)
    }

    override fun changeItemColor(id: Int, color: Int) {
        wheelItemsCache.changeItemColor(id, color)
    }

    override suspend fun endEditing(title: String) {
        val wheelId: Int
        if (wheelCache.isEmpty()) {
            wheelId = dao.insertWheel(WheelRoomModel(null, title)).toInt()
        } else {
            wheelId = wheelCache.getCachedId()
            dao.updateWheel(WheelRoomModel(wheelId, title))
            dao.deleteAllItemsWithId(wheelId)
            wheelCache.clear()
        }
        wheelItemsCache.getItemList().forEach {
            dao.insertItem(ItemRoomModel(null, wheelId, it.name, it.color))
        }
        wheelItemsCache.clear()
    }

    override fun cancelEditing() {
        wheelCache.clear()
        wheelItemsCache.clear()
    }

    override suspend fun getWheelList(): List<DomainItem> {
        val wheels = dao.getAllWheels()
        val list = mutableListOf<DataItem>()
        wheels.forEach {
            val itemList = dao.getAllItemsWithId(it.id!!)
            val data = DataItem.BaseWheelItem(it.id, it.title, itemList.map { item ->
                item.mapToData()
            })
            list.add(data)
        }
        return list.map { it.mapToDomain() }
    }

    override suspend fun cache(id: Int) {
        wheelCache.cacheId(id)
        wheelItemsCache.cache(dao.getAllItemsWithId(id).map { it.mapToData() }.reversed())
    }

    override suspend fun getRandomItemName(): String {
        return dao.getAllItemsWithId(wheelCache.getCachedId()).random().name
    }
}