package com.example.fetchexercise.fake

import com.example.fetchexercise.model.ListItem
import com.example.fetchexercise.network.ApiService

class FakeApiService: ApiService {
    private var responseToReturn: List<ListItem>? = null

    fun setResponse(response: List<ListItem>) {
        responseToReturn = response
    }

    override suspend fun getListInfo(): List<ListItem> {
        return responseToReturn ?: throw Exception("Error loading data")
    }
}