package com.master8.shana.app.dependency

import com.master8.shana.data.repository.MoviesRepositoryImpl
import com.master8.shana.data.source.firebase.database.FirebaseRealtimeDatabase
import com.master8.shana.data.source.firebase.database.FirebaseRealtimeDatabaseImpl
import com.master8.shana.data.source.tmdb.createTMDbApiService
import com.master8.shana.domain.usecase.*
import com.master8.shana.ui.movies.MovieViewModel

class MoviesModule {

    val movieViewModel by lazy { MovieViewModel(addGoodMovieUseCase, addNeedToWatchMovieUseCase) }

    private val addNeedToWatchMovieUseCase by lazy { AddNeedToWatchMovieUseCase(moviesRepository, prepareMovieToAddUseCase) }
    private val addGoodMovieUseCase by lazy { AddGoodMovieUseCase(moviesRepository, prepareMovieToAddUseCase) }

    private val prepareMovieToAddUseCase by lazy { PrepareMovieToAddUseCase() }

    val getChangedMovieUseCase by lazy { GetChangedMovieUseCase(moviesRepository) }
    val moviesRepository by lazy { MoviesRepositoryImpl(createTMDbApiService(), firebaseRealtimeDatabase) }
    private val firebaseRealtimeDatabase: FirebaseRealtimeDatabase by lazy { FirebaseRealtimeDatabaseImpl() }



}
