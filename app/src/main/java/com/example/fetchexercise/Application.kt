package com.example.fetchexercise

import android.app.Application
import com.example.fetchexercise.data.AppContainer
import com.example.fetchexercise.data.DefaultAppContainer

class Application: Application() {
    // App container instance used by the rest of the classes to obtain dependencies
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}