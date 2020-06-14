package com.master8.shana.app

import android.app.Application
import android.content.Context
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.master8.shana.app.dependency.MoviesModule
import com.master8.shana.app.dependency.SearchModule

class App : Application() {

    val moviesModule by lazy { MoviesModule() }
    val searchModule by lazy { SearchModule(moviesModule) }

    override fun onCreate() {
        super.onCreate()
        Firebase.database.setPersistenceEnabled(true)
    }
}

val Context.app: App
    get() = applicationContext as App