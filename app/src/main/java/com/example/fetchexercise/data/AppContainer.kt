package com.example.fetchexercise.data

import android.content.Context
import com.example.fetchexercise.data.AppDatabase.Companion.getDatabase
import com.example.fetchexercise.network.ApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface AppContainer {
    val repository: ListRepository
}

class DefaultAppContainer(context: Context) : AppContainer {
    private val baseUrl = "https://fetch-hiring.s3.amazonaws.com/"
    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val database = getDatabase(context)
    private val listDao = database.listDao()

    // OkHttp client with logging interceptor
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    // Moshi instance with Kotlin adapter factory
    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    // Retrofit instance with Moshi converter
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()

    // Retrofit service for making API calls
    private val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    // Repository implementation
    override val repository: ListRepository by lazy {
        DefaultListRepository(retrofitService, listDao)
    }
}