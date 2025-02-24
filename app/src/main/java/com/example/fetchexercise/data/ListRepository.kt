package com.example.fetchexercise.data

import com.example.fetchexercise.model.ListItem
import com.example.fetchexercise.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException

interface ListRepository {
    suspend fun getListInfo(): List<ListItem>
}

class DefaultListRepository(private val apiService: ApiService, private val listDao: ListDao) :
    ListRepository {

    override suspend fun getListInfo(): List<ListItem> {
        return withContext(Dispatchers.IO) {
            try {
                // Check if data is in cache
                val cache = listDao.getListInfo()
                // If data is not in the database, fetch from the network
                (cache.ifEmpty {
                    val response = apiService.getListInfo().filter { !it.name.isNullOrBlank() }
                    val entities = response.map {
                        ListItem(
                            id = it.id,
                            listId = it.listId,
                            name = it.name
                        )
                    }
                    // Add fetched data to the database
                    listDao.insertAll(entities)
                    entities
                })
                // Handle network errors
            } catch (e: IOException) {
                throw Exception("Network error: $e.message")
                // Handle any other errors
            } catch (e: Exception) {
                throw Exception("Error loading data: $e.message")
            }
        }
    }
}

