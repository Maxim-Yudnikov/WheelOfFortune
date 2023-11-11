package com.maxim.wheeloffortune.edit

import com.maxim.wheeloffortune.data.WheelEditDataSource
import com.maxim.wheeloffortune.domain.BaseFailureHandler
import com.maxim.wheeloffortune.domain.EmptyItemListException
import com.maxim.wheeloffortune.domain.edit.BaseEditInteractor
import com.maxim.wheeloffortune.domain.edit.EditInteractor
import com.maxim.wheeloffortune.domain.main.DomainItem
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.Assert.assertEquals

class EditInteractorTest {
    private lateinit var interactor: EditInteractor
    private lateinit var dataSource: FakeDataSource

    @Before
    fun before() {
        dataSource = FakeDataSource()
        interactor = BaseEditInteractor(dataSource = dataSource, BaseFailureHandler())
    }

    @Test
    fun test_delete_wheel() = runBlocking {
        interactor.deleteWheel()
        dataSource.checkDeleteWheelWasCalled(1)
    }

    @Test
    fun test_create_item() {
        interactor.createItem()
        dataSource.checkCreateNewItemWasCalled(1)
    }

    @Test
    fun test_delete_item() = runBlocking {
        interactor.deleteItem(2)
        dataSource.checkDeleteItem(1, 2)
    }

    @Test
    fun test_change_item_name() {
        interactor.changeItemName(23, "Name")
        dataSource.checkChangeItemName(1, 23, "Name")
    }

    @Test
    fun test_change_item_color() {
        interactor.changeItemColor(32, 44)
        dataSource.checkChangeItemColor(1, 32, 44)
    }

    @Test
    fun test_end_editing() = runBlocking {
        interactor.endEditing("Title")
        dataSource.checkEndEditing(1, "Title")
    }

    @Test
    fun test_get_item_list_success() = runBlocking {
        val actual = interactor.getList()
        val expected =
            listOf(DomainItem.BaseDomainItem("Name", 0), DomainItem.BaseDomainItem("Name 2", 1))
        assertEquals(expected, actual)
    }

    @Test
    fun test_get_item_list_failed() = runBlocking {
        dataSource.returnSuccess = false
        val actual = interactor.getList()
        val expected = listOf(DomainItem.FailedDomainItem("Empty item list"))
        assertEquals(expected, actual)
    }

    private class FakeDataSource : WheelEditDataSource {
        private var deleteWheelCounter = 0
        private var createNewItemCounter = 0
        private var deleteItemCounter = 0
        private var deleteItemValue = -1
        private var changeItemNameCounter = 0
        private var changeItemNameIntValue = -1
        private var changeItemNameStringValue = ""
        private var changeItemColorCounter = 0
        private var changeItemColorFirstValue = -1
        private var changeItemColorSecondValue = -1
        private var endEditingCounter = 0
        private var endEditingValue = ""
        var returnSuccess = true
        override suspend fun deleteWheel() {
            deleteWheelCounter++
        }

        fun checkDeleteWheelWasCalled(count: Int) {
            assertEquals(count, deleteWheelCounter)
        }

        override fun createNewItem() {
            createNewItemCounter++
        }

        fun checkCreateNewItemWasCalled(count: Int) {
            assertEquals(count, createNewItemCounter)
        }

        override fun deleteItem(id: Int) {
            deleteItemCounter++
            deleteItemValue = id
        }

        override fun getList(): List<DomainItem.BaseDomainItem> {
            if (returnSuccess)
                return listOf(
                    DomainItem.BaseDomainItem("Name", 0),
                    DomainItem.BaseDomainItem("Name 2", 1)
                )
            else
                throw EmptyItemListException()
        }

        fun checkDeleteItem(count: Int, value: Int) {
            assertEquals(count, deleteItemCounter)
            assertEquals(value, deleteItemValue)
        }

        override fun changeItemName(id: Int, name: String) {
            changeItemNameCounter++
            changeItemNameIntValue = id
            changeItemNameStringValue = name
        }

        fun checkChangeItemName(count: Int, first: Int, second: String) {
            assertEquals(count, changeItemNameCounter)
            assertEquals(first, changeItemNameIntValue)
            assertEquals(second, changeItemNameStringValue)
        }

        override fun changeItemColor(id: Int, color: Int) {
            changeItemColorCounter++
            changeItemColorFirstValue = id
            changeItemColorSecondValue = color
        }

        fun checkChangeItemColor(count: Int, first: Int, second: Int) {
            assertEquals(count, changeItemColorCounter)
            assertEquals(first, changeItemColorFirstValue)
            assertEquals(second, changeItemColorSecondValue)
        }

        override suspend fun endEditing(title: String) {
            endEditingCounter++
            endEditingValue = title
        }

        override fun cancelEditing() {
            TODO("Not yet implemented")
        }

        fun checkEndEditing(count: Int, value: String) {
            assertEquals(count, endEditingCounter)
            assertEquals(value, endEditingValue)
        }
    }
}