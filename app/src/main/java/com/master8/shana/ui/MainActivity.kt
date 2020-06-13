package com.master8.shana.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.master8.shana.R
import com.master8.shana.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Shana_DayNight)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}