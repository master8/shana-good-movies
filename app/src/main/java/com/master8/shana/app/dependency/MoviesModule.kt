package com.master8.shana.app.dependency

import com.master8.shana.data.repository.MoviesRepositoryImpl
import com.master8.shana.data.source.firebase.database.FirebaseRealtimeDatabase
import com.master8.shana.data.source.firebase.database.FirebaseRealtimeDatabaseImpl
import com.master8.shana.data.source.tmdb.createTMDbApiService
import com.master8.shana.domain.usecase.*

class MoviesModule {
    val addNeedToWatchMovieUseCase by lazy { AddNeedToWatchMovieUseCase(moviesRepository, prepareMovieToAddUseCase) }
    val addGoodMovieUseCase by lazy { AddGoodMovieUseCase(moviesRepository, prepareMovieToAddUseCase) }
    val updateMovieUseCase by lazy { LinkMovieUseCase(moviesRepository, prepareSeriesToAddUseCase) }

    private val prepareMovieToAddUseCase by lazy { PrepareMovieToAddUseCase(prepareSeriesToAddUseCase) }
    private val prepareSeriesToAddUseCase by lazy { PrepareSeriesToAddUseCase() }

    val getChangedMovieUseCase by lazy { GetChangedMovieUseCase(moviesRepository) }
    val moviesRepository by lazy { MoviesRepositoryImpl(createTMDbApiService(), firebaseRealtimeDatabase) }
    val firebaseRealtimeDatabase: FirebaseRealtimeDatabase by lazy { FirebaseRealtimeDatabaseImpl() }
}
