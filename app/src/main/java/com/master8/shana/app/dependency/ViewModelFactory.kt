package com.master8.shana.app.dependency

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.master8.shana.data.repository.MoviesRepositoryImpl
import com.master8.shana.data.source.firebase.database.FirebaseRealtimeDatabase
import com.master8.shana.data.source.firebase.database.FirebaseRealtimeDatabaseImpl
import com.master8.shana.data.source.tmdb.createTMDbApiService
import com.master8.shana.domain.usecase.*
import com.master8.shana.ui.movies.MovieViewModel
import com.master8.shana.ui.search.SearchViewModel

class ViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    //TODO move to dependencies
    private val firebaseRealtimeDatabase: FirebaseRealtimeDatabase by lazy { FirebaseRealtimeDatabaseImpl() }
    private val moviesRepository by lazy { MoviesRepositoryImpl(createTMDbApiService(), firebaseRealtimeDatabase) }
    private val searchMoviesUseCase by lazy { SearchMoviesUseCase(moviesRepository) }

    private val getChangedMovieUseCase by lazy { GetChangedMovieUseCase(moviesRepository) }

    private val prepareMovieToAddUseCase by lazy { PrepareMovieToAddUseCase() }
    private val addGoodMovieUseCase by lazy { AddGoodMovieUseCase(moviesRepository, prepareMovieToAddUseCase) }
    private val addNeedToWatchMovieUseCase by lazy { AddNeedToWatchMovieUseCase(moviesRepository, prepareMovieToAddUseCase) }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(SearchViewModel::class.java) -> {
                SearchViewModel(searchMoviesUseCase, getChangedMovieUseCase)
            }
            isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(addGoodMovieUseCase, addNeedToWatchMovieUseCase)
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T

}