package com.maxim.wheeloffortune.edit

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.maxim.wheeloffortune.domain.edit.EditInteractor
import com.maxim.wheeloffortune.domain.main.DomainItem
import com.maxim.wheeloffortune.presentation.edit.EditCommunication
import com.maxim.wheeloffortune.presentation.edit.EditViewModel
import com.maxim.wheeloffortune.presentation.main.UiItem
import kotlinx.coroutines.Dispatchers
import org.junit.*
import org.junit.Assert.assertEquals

class EditViewModelTest {
    private lateinit var viewModel: EditViewModel
    private lateinit var interactor: FakeInteractor
    private lateinit var communication: FakeCommunication

    @Before
    fun before() {
        interactor = FakeInteractor()
        communication = FakeCommunication()
        viewModel = EditViewModel(interactor = interactor, communication, Dispatchers.Unconfined)
    }

    @Test
    fun test_delete_wheel() {
        viewModel.deleteWheel()
        interactor.checkDeleteWheelWasCalled(1)
    }

    @Test
    fun test_create_item() {
        viewModel.createItem()
        interactor.checkCreateItemWasCalled(1)
    }

    @Test
    fun test_delete_item() {
        viewModel.deleteItem(id = 9174)
        interactor.checkDeleteItem(1, 9174)
    }

    @Test
    fun test_change_item_name() {
        viewModel.changeItemName(id = 228, title = "New title")
        interactor.checkChangeItemName(1, 228, "New title")
    }

    @Test
    fun test_change_item_color() {
        viewModel.changeItemColor(id = 666, color = 2)
        interactor.checkChangeItemColor(1, 666, 2)
    }

    @Test
    fun test_end_editing() {
        var check = 0
        viewModel.endEditing(title = "title") {
            check = 1
        }
        interactor.checkEndEditing(1, "title")
        assertEquals(1, check)
    }

    @Test
    fun test_get_item_list() {
        viewModel.getItemList()
        communication.checkList(
            listOf(
                UiItem.BaseUiItem("Name", 0),
                UiItem.BaseUiItem("Name 2", 1)
            )
        )
    }

    private class FakeCommunication : EditCommunication {
        private val list = mutableListOf<UiItem>()
        override fun showList(list: List<UiItem>) {
            this.list.clear()
            this.list.addAll(list)
        }

        fun checkList(list: List<UiItem>) {
            assertEquals(list, this.list)
        }

        override fun observeList(owner: LifecycleOwner, observer: Observer<List<UiItem>>) {
            TODO("Not yet implemented")
        }

        override fun getList(): List<UiItem> {
            TODO("Not yet implemented")
        }

        override fun clear() {
            TODO("Not yet implemented")
        }
    }

    private class FakeInteractor : EditInteractor {
        private var deleteWheelCounter = 0
        private var createItemCounter = 0
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
        override suspend fun deleteWheel() {
            deleteWheelCounter++
        }

        fun checkDeleteWheelWasCalled(count: Int) {
            assertEquals(count, deleteWheelCounter)
        }

        override fun createItem() {
            createItemCounter++
        }

        fun checkCreateItemWasCalled(count: Int) {
            assertEquals(count, createItemCounter)
        }

        override suspend fun deleteItem(id: Int) {
            deleteItemCounter++
            deleteItemValue = id
        }

        override fun getList(): List<DomainItem> {
            return listOf(
                DomainItem.BaseDomainItem("Name", 0),
                DomainItem.BaseDomainItem("Name 2", 1)
            )
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

        fun checkChangeItemName(count: Int, firstArg: Int, secondArg: String) {
            assertEquals(count, changeItemNameCounter)
            assertEquals(firstArg, changeItemNameIntValue)
            assertEquals(secondArg, changeItemNameStringValue)
        }

        override fun changeItemColor(id: Int, colorId: Int) {
            changeItemColorCounter++
            changeItemColorFirstValue = id
            changeItemColorSecondValue = colorId
        }

        fun checkChangeItemColor(count: Int, firstArg: Int, secondArg: Int) {
            assertEquals(count, changeItemColorCounter)
            assertEquals(firstArg, changeItemColorFirstValue)
            assertEquals(secondArg, changeItemColorSecondValue)
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