package com.master8.shana.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.master8.shana.R
import com.master8.shana.data.repository.MoviesRepositoryImpl
import com.master8.shana.data.repository.converters.parseMovie
import com.master8.shana.data.source.firebase.database.FirebaseRealtimeDatabase
import com.master8.shana.data.source.firebase.database.FirebaseRealtimeDatabaseImpl
import com.master8.shana.data.source.tmdb.createTMDbApiService
import com.master8.shana.databinding.ActivityMainBinding
import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.usecase.SearchMoviesUseCase
import com.master8.shana.ui.movies.MoviesAdapter
import com.master8.shana.ui.movies.MoviesFirebaseAdapter
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Shana_DayNight)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}