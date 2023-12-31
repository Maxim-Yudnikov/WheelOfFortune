package com.maxim.wheeloffortune.data

import com.maxim.wheeloffortune.data.room.ItemRoomModel
import com.maxim.wheeloffortune.data.room.RoomDao
import com.maxim.wheeloffortune.data.room.WheelRoomModel
import com.maxim.wheeloffortune.domain.EmptyItemListException
import com.maxim.wheeloffortune.domain.EmptyItemNameException
import com.maxim.wheeloffortune.domain.main.DomainItem

class BaseWheelDataSource(
    private val dao: RoomDao,
    private val wheelCache: WheelCache,
    private val wheelItemsCache: WheelItemsCache
) : WheelDataSource {
    override suspend fun deleteWheel() {
        dao.deleteWheel(wheelCache.getCachedId())
        wheelCache.clear()
        wheelItemsCache.clear()
    }

    override fun createNewItem() {
        val item = DataItem.BaseDataItem("", 0)
        wheelItemsCache.createItem(item)
    }

    override fun deleteItem(id: Int) {
        wheelItemsCache.deleteItem(id)
    }

    override fun getItemList(): List<DomainItem.BaseDomainItem> {
        val list = wheelItemsCache.getItemList()
        if (list.isEmpty())
            throw EmptyItemListException()
        else
            return list.map { it.mapToDomain() as DomainItem.BaseDomainItem }
    }

    override fun changeItemName(id: Int, name: String) {
        wheelItemsCache.changeItemName(id, name)
    }

    override fun changeItemColor(id: Int, color: Int) {
        wheelItemsCache.changeItemColor(id, color)
    }

    override suspend fun endEditing(title: String) {
        if (wheelItemsCache.getItemList().isEmpty())
            throw EmptyItemListException()
        wheelItemsCache.getItemList().forEachIndexed { index, item ->
            if(item.name.isEmpty())
                throw EmptyItemNameException(index.toString())
        }

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
        if (id != -1) {
            wheelCache.cacheId(id)
            wheelItemsCache.cache(dao.getAllItemsWithId(id).map { it.mapToData() }.reversed())
        }
    }

    override fun closeWheel() {
        wheelCache.clear()
        wheelItemsCache.clear()
    }
}