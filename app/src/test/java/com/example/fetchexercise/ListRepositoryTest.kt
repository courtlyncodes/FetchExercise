package com.example.fetchexercise

import com.example.fetchexercise.data.DefaultListRepository
import com.example.fetchexercise.fake.FakeApiService
import com.example.fetchexercise.fake.FakeListDao
import com.example.fetchexercise.fake.fakeListInfo
import org.junit.Test
import org.junit.Assert.*
import kotlinx.coroutines.test.runTest

class ListRepositoryTest {

    @Test
    fun listRepository_loadListItems_verifyListItemListIsNotEmpty(): Unit = runTest {
        val fakeListDao = FakeListDao()
        val fakeApiService = FakeApiService()

        val response = fakeListInfo()
        fakeApiService.setResponse(response)

        val repository = DefaultListRepository(
            apiService = fakeApiService,
            listDao = fakeListDao
        )

        val listItems = repository.getListInfo()

        assertTrue(listItems.isNotEmpty())
    }

    @Test
    fun listRepository_loadListItems_verifyCache(): Unit = runTest {
        val fakeListDao = FakeListDao()
        val fakeApiService = FakeApiService()

        val response = fakeListInfo()
        fakeApiService.setResponse(response)
        fakeListDao.insertAll(response)

        val repository = DefaultListRepository(
            apiService = fakeApiService,
            listDao = fakeListDao
        )

        val listItems = repository.getListInfo()
        assertEquals(listItems, fakeListDao.getListInfo())

        val listItems2 = repository.getListInfo()
        assertEquals(listItems, listItems2)
    }

    @Test
    fun listRepository_loadListItems_returnsListWithNonBlankNames(): Unit = runTest {
        val fakeListDao = FakeListDao()
        val fakeApiService = FakeApiService()

        val response = fakeListInfo()
        fakeApiService.setResponse(response)

        val repository = DefaultListRepository(
            apiService = fakeApiService,
            listDao = fakeListDao
        )

        val listItems = repository.getListInfo()

        assertTrue(listItems.all { it.name?.isNotBlank() == true })
    }
}