package com.master8.shana.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.master8.shana.data.source.tmdb.createTMDbApiService
import com.master8.shana.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MainScope().launch(Dispatchers.IO) {
            val result = createTMDbApiService().multipleSearch("vinland")
            Log.d("mv8", "after ${result}")
        }
    }
}