package com.master8.shana.app.dependency

import com.master8.shana.data.repository.MovieChangesRepositoryImpl
import com.master8.shana.data.repository.MoviesRepositoryImpl
import com.master8.shana.data.source.firebase.database.FirebaseRealtimeDatabase
import com.master8.shana.data.source.firebase.database.FirebaseRealtimeDatabaseImpl
import com.master8.shana.domain.usecase.movies.*

class MoviesModule {
    val addNeedToWatchMovieUseCase by lazy {
        AddNeedToWatchMovieUseCase(
            moviesRepository,
            movieChangesRepository,
            prepareMovieToAddUseCase
        )
    }
    val addGoodMovieUseCase by lazy {
        AddGoodMovieUseCase(
            moviesRepository,
            movieChangesRepository,
            prepareMovieToAddUseCase
        )
    }
    val updateMovieUseCase by lazy {
        LinkMovieUseCase(
            moviesRepository,
            prepareSeriesToAddUseCase
        )
    }
    val deleteMovieUseCase by lazy {
        DeleteMovieUseCase(
            moviesRepository,
            movieChangesRepository
        )
    }
    val moveToWatchMovieUseCase by lazy {
        MoveToGoodMoviesUseCase(
            moviesRepository,
            movieChangesRepository
        )
    }
    val changeMoviePosterUseCase by lazy {
        ChangeMoviePosterUseCase(
            moviesRepository
        )
    }

    private val prepareMovieToAddUseCase by lazy {
        PrepareMovieToAddUseCase(
            prepareSeriesToAddUseCase
        )
    }

    private val prepareSeriesToAddUseCase by lazy { PrepareSeriesToAddUseCase() }

    val getChangedMovieUseCase by lazy {
        GetChangedMovieUseCase(
            movieChangesRepository
        )
    }

    private val moviesRepository by lazy { MoviesRepositoryImpl(firebaseRealtimeDatabase) }
    private val movieChangesRepository by lazy { MovieChangesRepositoryImpl() }
    val firebaseRealtimeDatabase: FirebaseRealtimeDatabase by lazy { FirebaseRealtimeDatabaseImpl() }
}
