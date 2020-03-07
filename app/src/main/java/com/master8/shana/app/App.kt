package com.master8.shana.app

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.database.FirebaseDatabase
import com.master8.shana.app.dependency.MoviesModule
import com.master8.shana.app.dependency.SearchModule

class App : Application() {

    val moviesModule by lazy { MoviesModule() }
    val searchModule by lazy { SearchModule(moviesModule) }

    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}

val Context.app: App
    get() = applicationContext as App