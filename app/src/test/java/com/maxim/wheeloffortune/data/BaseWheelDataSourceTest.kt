package com.maxim.wheeloffortune.data

import com.maxim.wheeloffortune.data.room.ItemRoomModel
import com.maxim.wheeloffortune.data.room.RoomDao
import com.maxim.wheeloffortune.data.room.WheelRoomModel
import com.maxim.wheeloffortune.domain.EmptyItemListException
import com.maxim.wheeloffortune.domain.EmptyItemNameException
import com.maxim.wheeloffortune.domain.main.DomainItem
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.Assert.assertEquals

class BaseWheelDataSourceTest {
    private lateinit var dataSource: BaseWheelDataSource
    private lateinit var dao: FakeDao

    @Before
    fun before() {
        dao = FakeDao()
        dataSource = BaseWheelDataSource(dao, BaseWheelCache(), BaseWheelItemsCache())
    }

    @Test
    fun test_create_wheel_and_two_items_and_delete_item() = runBlocking {
        dataSource.createNewItem()
        dataSource.changeItemName(0, "First")
        dataSource.createNewItem()
        dataSource.changeItemName(1, "Second")
        dataSource.changeItemColor(1, 1)
        dataSource.createNewItem()
        dataSource.deleteItem(2)
        dataSource.endEditing("Title")

        val wheelExpected = listOf(WheelRoomModel(0, "Title"))
        val itemExpected = listOf(
            ItemRoomModel(0, 0, "First", 0),
            ItemRoomModel(1, 0, "Second", 1)
        )
        assertEquals(wheelExpected, dao.wheelList)
        assertEquals(itemExpected, dao.itemList)
    }

    @Test
    fun test_get_wheel_list() = runBlocking {
        dataSource.createNewItem()
        dataSource.changeItemName(0, "First")
        dataSource.createNewItem()
        dataSource.changeItemName(1, "Second")
        dataSource.changeItemColor(1, 1)
        dataSource.endEditing("Title")
        val actual = dataSource.getWheelList()
        val expected = listOf<DomainItem>(
            DomainItem.BaseDomainWheel(
                0, "Title", listOf(
                    DomainItem.BaseDomainItem("First", 0),
                    DomainItem.BaseDomainItem("Second", 1),
                )
            )
        )
        assertEquals(expected, actual)
    }

    @Test
    fun test_update_wheel() = runBlocking {
        dataSource.createNewItem()
        dataSource.changeItemName(0, "First")
        dataSource.createNewItem()
        dataSource.changeItemName(1, "Second")
        dataSource.changeItemColor(1, 1)
        dataSource.endEditing("Title")

        dataSource.cache(0)
        dataSource.changeItemName(0, "New first")
        dataSource.endEditing("Title 2")

        val actual = dataSource.getWheelList()
        val expected = listOf<DomainItem>(
            DomainItem.BaseDomainWheel(
                0, "Title 2", listOf(
                    DomainItem.BaseDomainItem("New first", 0),
                    DomainItem.BaseDomainItem("Second", 1),
                )
            )
        )
        assertEquals(expected, actual)
    }

    @Test(expected = EmptyItemListException::class)
    fun test_end_editing_empty_list() = runBlocking {
        dataSource.endEditing("Title")
    }

    @Test
    fun test_end_editing_empty_item_name() = runBlocking {
        dataSource.createNewItem()
        try {
            dataSource.endEditing("Title")
        } catch (e: Exception) {
            assertEquals(EmptyItemNameException("0"), e)
        }
    }

    @Test
    fun test_delete_wheel() = runBlocking {
        dataSource.createNewItem()
        dataSource.changeItemName(0, "name")
        dataSource.endEditing("Title")

        dataSource.cache(0)
        dataSource.deleteWheel()
        assertEquals(emptyList<WheelRoomModel>(), dao.wheelList)
    }

    @Test
    fun test_cancel_editing() = runBlocking {
        dataSource.createNewItem()
        dataSource.changeItemName(0, "name")
        dataSource.endEditing("Title")

        dataSource.cache(0)
        dataSource.createNewItem()
        dataSource.changeItemName(1, "new name")
        dataSource.cancelEditing()

        assertEquals(listOf(WheelRoomModel(0, "Title")), dao.wheelList)
        assertEquals(listOf(ItemRoomModel(0, 0, "name", 0)), dao.itemList)
    }

    @Test(expected = EmptyItemListException::class)
    fun test_get_item_list_empty() = runBlocking {
        val list = dataSource.getItemList()
    }

    @Test
    fun test_get_item_list_success() = runBlocking {
        dataSource.createNewItem()
        dataSource.changeItemName(0, "name")
        dataSource.changeItemColor(0, 2)
        val actual = dataSource.getItemList()
        val expected = listOf(DomainItem.BaseDomainItem("name", 2))
    }


    private class FakeDao : RoomDao {
        var wheelList = mutableListOf<WheelRoomModel>()
        var itemList = mutableListOf<ItemRoomModel>()
        override suspend fun insertWheel(wheel: WheelRoomModel): Long {
            val newWheel = WheelRoomModel(wheelList.size, wheel.title)
            wheelList.add(newWheel)
            return wheelList.size - 1L
        }

        override suspend fun updateWheel(wheel: WheelRoomModel) {
            var id = -1
            wheelList.forEach {
                if (it.id == wheel.id) {
                    id = it.id!!
                }
            }
            wheelList[id] = wheel
        }

        override suspend fun getAllWheels(): List<WheelRoomModel> {
            return wheelList
        }

        override suspend fun deleteWheel(id: Int) {
            var idToDelete = -1
            wheelList.forEachIndexed { index, item ->
                if (item.id == id) {
                    idToDelete = item.id!!
                }
            }
            wheelList.removeAt(idToDelete)
        }

        override suspend fun insertItem(item: ItemRoomModel) {
            val newItem = ItemRoomModel(itemList.size, item.wheelId, item.name, item.color)
            itemList.add(newItem)
        }

        override suspend fun getAllItemsWithId(id: Int): List<ItemRoomModel> {
            return itemList.filter { it.wheelId == id }
        }

        override suspend fun deleteAllItemsWithId(id: Int) {
            var size = itemList.size -1
            for(i in size downTo  0) {
                if (itemList[i].wheelId == id) {
                    itemList.removeAt(i)
                }
            }
        }
    }
}