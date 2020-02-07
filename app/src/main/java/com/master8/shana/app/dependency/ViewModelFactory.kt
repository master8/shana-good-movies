package com.master8.shana.app.dependency

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.master8.shana.data.repository.MoviesRepositoryImpl
import com.master8.shana.data.source.firebase.database.FirebaseRealtimeDatabase
import com.master8.shana.data.source.firebase.database.FirebaseRealtimeDatabaseImpl
import com.master8.shana.data.source.tmdb.createTMDbApiService
import com.master8.shana.domain.usecase.GetChangedMovieUseCase
import com.master8.shana.domain.usecase.SearchMoviesUseCase
import com.master8.shana.ui.search.SearchViewModel

class ViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(SearchViewModel::class.java) -> {
//                TODO more to dependencies
                val firebaseRealtimeDatabase: FirebaseRealtimeDatabase = FirebaseRealtimeDatabaseImpl()
                val repository = MoviesRepositoryImpl(createTMDbApiService(), firebaseRealtimeDatabase)
                val searchMoviesUseCase = SearchMoviesUseCase(repository)

                val getChangedMovieUseCase = GetChangedMovieUseCase(repository)

                SearchViewModel(searchMoviesUseCase, getChangedMovieUseCase)
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T

}