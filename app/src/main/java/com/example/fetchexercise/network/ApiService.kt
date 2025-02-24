package com.example.fetchexercise.network

import com.example.fetchexercise.model.ListItem
import retrofit2.http.GET

interface ApiService {
    @GET("hiring.json")
    suspend fun getListInfo(): List<ListItem>
}