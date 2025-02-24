package com.example.fetchexercise.fake

import com.example.fetchexercise.data.ListDao
import com.example.fetchexercise.model.ListItem

class FakeListDao: ListDao {
    private val listItems = mutableListOf<ListItem>()

    override suspend fun getListInfo(): List<ListItem> {
        return listItems
    }

    override suspend fun insertAll(listItem: List<ListItem>) {
        listItems.addAll(listItem)
    }
}