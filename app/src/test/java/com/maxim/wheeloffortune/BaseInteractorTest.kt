package com.maxim.wheeloffortune

import com.maxim.wheeloffortune.domain.DomainItem
import com.maxim.wheeloffortune.domain.Interactor
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.Assert.assertEquals

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


    private class FakeDataSource : WheelDataSource {
        private var cacheCounter = 0
        private var cacheValue = -1
        override suspend fun getItemList(): List<DomainItem.BaseDomainItem> {
            return listOf(DomainItem.BaseDomainItem("Title", emptyList()))
        }

        override fun cache(id: Int) {
            cacheCounter++
            cacheValue = id
        }

        fun checkCache(count: Int, value: Int) {
            assertEquals(count, cacheCounter)
            assertEquals(value, cacheValue)
        }
    }
}