package com.maxim.wheeloffortune

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import kotlinx.coroutines.Dispatchers
import org.junit.*
import org.junit.Assert.assertEquals
import java.lang.IllegalStateException

class MainViewModelTest {
    private lateinit var viewModel: MainViewModel
    private lateinit var communication: Communication
    private lateinit var interactor: Interactor

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
        val actual = communication.list
        val expected = listOf<UiItem>(UiItem.BaseUiItem("title", emptyList<WheelItem>()), UiItem.Empty)
        assertEquals(expected, actual)
    }

    @Test
    fun test_open_wheel() {
        viewModel.openWheel(id = 123)
        interactor.checkOpenItemWasCalledCount(1)
        interactor.checkOpenItemWasCalledWith(123)
    }

    fun test_rotate() {
        viewModel.rotate()
        val expected = listOf<State>(State.Rotating, State.Done("Item"))
        assertEquals(expected, communication.stateList)
        interactor.checkRotateWasCalledCount(1)
    }



    private class FakeCommunication : Communication {
        var list: List<UiItem> = emptyList()
        var stateList = mutableListOf<State>()

        override fun showState(state: State) {
            stateList.add(state)
        }
        override fun observeState(owner: LifecycleOwner, observer: Observer<State>) {
            throw IllegalStateException("Not used")
        }
        override fun showList(list: List<UiItem>) {
            this.list = list
        }

        override fun getList(): List<UiItem> {
            return list
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
        private var rotateCounter = 0
        override suspend fun getItemList(): List<DomainItem> {
            return listOf(DomainItem.BaseDomainItem("title", emptyList<WheelItem>()), DomainItem.Empty)
        }
        override fun openItem(id: Int) {
            openItemCounter++
            openItemValue = id
        }

        override fun rotate() {
            rotateCounter++
        }
        private fun checkRotateWasCalledCount(count: Int) {
            assertEquals(count, rotateCounter)
        }
        private fun checkOpenItemWasCalledCount(count: Int) {
            assertEquals(count, openItemCounter)
        }

        private fun checkOpenItemWasCalledWith(argument: Int) {
            assertEquals(argument, openItemValue)
        }
    }
}