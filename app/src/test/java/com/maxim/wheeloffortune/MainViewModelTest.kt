package com.maxim.wheeloffortune

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import com.maxim.wheeloffortune.domain.DomainItem
import com.maxim.wheeloffortune.domain.Interactor
import com.maxim.wheeloffortune.presentation.Communication
import com.maxim.wheeloffortune.presentation.MainViewModel
import com.maxim.wheeloffortune.presentation.State
import com.maxim.wheeloffortune.presentation.UiItem
import kotlinx.coroutines.Dispatchers
import org.junit.*
import org.junit.Assert.assertEquals
import java.lang.IllegalStateException

class MainViewModelTest {
    private lateinit var viewModel: MainViewModel
    private lateinit var communication: FakeCommunication
    private lateinit var interactor: FakeInteractor

    @Before
    fun before() {
        communication = FakeCommunication()
        interactor = FakeInteractor()
        viewModel = MainViewModel(
            interactor = interactor,
            communication = communication,
            Dispatchers.Unconfined
        )
    }

    @Test
    fun test_get_item_list() {
        viewModel.getItemList()
        val actual = communication.itemList
        val expected = listOf<UiItem>(UiItem.BaseUiItem("title", emptyList()), UiItem.Empty)
        assertEquals(expected, actual)
    }

    @Test
    fun test_open_item() {
        viewModel.openItem(id = 123)
        interactor.checkOpenItemWasCalledCount(1)
        interactor.checkOpenItemWasCalledWith(123)
    }

    @Test
    fun test_rotate() {
        viewModel.rotate()
        val expected = listOf(State.Rotating, State.Done("Item"))
        assertEquals(expected, communication.stateList)
    }



    private class FakeCommunication : Communication {
        var itemList: List<UiItem> = emptyList()
        var stateList = mutableListOf<State>()

        override fun showState(state: State) {
            stateList.add(state)
        }
        override fun observeState(owner: LifecycleOwner, observer: Observer<State>) {
            throw IllegalStateException("Not used")
        }
        override fun showList(list: List<UiItem>) {
            this.itemList = list
        }

        override fun getList(): List<UiItem> {
            return itemList
        }

        override fun observeList(owner: LifecycleOwner, observer: Observer<List<UiItem>>) {
            throw IllegalStateException("Not used")
        }

        override fun getDiffResult(): DiffUtil.DiffResult {
            throw IllegalStateException("Not used")
        }
    }

    private class FakeInteractor : Interactor {
        private var openItemCounter = 0
        private var openItemValue = -1
        override suspend fun getItemList(): List<DomainItem> {
            return listOf(DomainItem.BaseDomainItem("title", emptyList()), DomainItem.Empty)
        }
        override fun openItem(id: Int) {
            openItemCounter++
            openItemValue = id
        }

        override suspend fun rotate(): String {
            return "Item"
        }
        fun checkOpenItemWasCalledCount(count: Int) {
            assertEquals(count, openItemCounter)
        }

        fun checkOpenItemWasCalledWith(argument: Int) {
            assertEquals(argument, openItemValue)
        }
    }
}