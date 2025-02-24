package com.example.fetchexercise

import com.example.fetchexercise.network.ApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiServiceTest {

    @Test
    fun apiService_fetchListItems_returnsNonEmptyList() = runBlocking {
        // Moshi instance with Kotlin adapter factory
       val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val response = apiService.getListInfo()

        assertTrue(response.isNotEmpty())
    }
}