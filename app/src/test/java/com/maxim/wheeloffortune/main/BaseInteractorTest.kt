package com.maxim.wheeloffortune.main

import com.maxim.wheeloffortune.data.WheelMainDataSource
import com.maxim.wheeloffortune.domain.main.BaseInteractor
import com.maxim.wheeloffortune.domain.main.DomainItem
import com.maxim.wheeloffortune.domain.main.Interactor
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BaseInteractorTest {
    private lateinit var interactor: Interactor
    private lateinit var dataSource: FakeDataSource

    @Before
    fun before() {
        dataSource = FakeDataSource()
        interactor = BaseInteractor(dataSource = dataSource)
    }

    @Test
    fun test_get_item_list() = runBlocking {
        val actual = interactor.getItemList()
        val expected =
            listOf(DomainItem.BaseDomainItem("Title", emptyList()), DomainItem.Empty)
        assertEquals(expected, actual)
    }

    @Test
    fun test_open_item() {
        interactor.openItem(123)
        dataSource.checkCache(1, 123)
    }

    @Test
    fun test_rotate() = runBlocking {
        val actual = interactor.rotate()
        assertEquals("Random Name", actual)
    }


    private class FakeDataSource : WheelMainDataSource {
        private var cacheCounter = 0
        private var cacheValue = -1
        override suspend fun getItemList(): List<DomainItem.BaseDomainItem> {
            return listOf(DomainItem.BaseDomainItem("Title", emptyList()))
        }

        override fun cache(id: Int) {
            cacheCounter++
            cacheValue = id
        }

        override fun getRandomItemName(): String {
            return "Random Name"
        }

        fun checkCache(count: Int, value: Int) {
            assertEquals(count, cacheCounter)
            assertEquals(value, cacheValue)
        }
    }
}