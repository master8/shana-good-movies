package com.master8.shana.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.master8.shana.data.repository.MoviesRepositoryImpl
import com.master8.shana.data.source.firebase.database.FirebaseRealtimeDatabaseImpl
import com.master8.shana.data.source.firebase.database.dto.FirebaseMovieDto
import com.master8.shana.data.source.firebase.database.dto.FirebaseSeriesDto
import com.master8.shana.data.source.tmdb.createTMDbApiService
import com.master8.shana.databinding.ActivityMainBinding
import com.master8.shana.domain.usecase.AddGoodMovieUseCase
import com.master8.shana.domain.usecase.AddNeedToWatchMovieUseCase
import com.master8.shana.domain.usecase.PrepareMovieToAddUseCase
import com.master8.shana.domain.usecase.SearchMoviesUseCase
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        MainScope().launch {
//            val repository = MoviesRepositoryImpl(createTMDbApiService(), FirebaseRealtimeDatabaseImpl())
//            val search = SearchMoviesUseCase(repository)
//            val movies = search("star wars")
//            Log.d("mv8", "after ${movies}")
//
//            val prepareMovieToAddUseCase = PrepareMovieToAddUseCase()
//
//            val addGoodMovie = AddGoodMovieUseCase(repository, prepareMovieToAddUseCase)
//            addGoodMovie(movies[0])
//            addGoodMovie(movies[1])
//
//            val addNeedToWatchMovie = AddNeedToWatchMovieUseCase(repository, prepareMovieToAddUseCase)
//            addNeedToWatchMovie(movies.last())
//        }
    }
}