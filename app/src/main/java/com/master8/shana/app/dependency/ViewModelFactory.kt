package com.master8.shana.app.dependency

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.master8.shana.app.app
import com.master8.shana.data.repository.MoviesRepositoryImpl
import com.master8.shana.data.source.firebase.database.FirebaseRealtimeDatabase
import com.master8.shana.data.source.firebase.database.FirebaseRealtimeDatabaseImpl
import com.master8.shana.data.source.tmdb.createTMDbApiService
import com.master8.shana.domain.usecase.*
import com.master8.shana.ui.movies.MovieViewModel
import com.master8.shana.ui.search.SearchViewModel

class ViewModelFactory(
    context: Context
) : ViewModelProvider.Factory {

    private val app = context.app

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(SearchViewModel::class.java) -> { app.searchModule.searchViewModel }
            isAssignableFrom(MovieViewModel::class.java) -> { app.moviesModule.movieViewModel }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T

}