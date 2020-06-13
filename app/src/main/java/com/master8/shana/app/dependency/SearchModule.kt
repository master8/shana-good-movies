package com.master8.shana.app.dependency

import com.master8.shana.data.repository.SearchRepositoryImpl
import com.master8.shana.data.source.tmdb.createTMDbApiService
import com.master8.shana.domain.usecase.search.SearchByMovieUseCase
import com.master8.shana.domain.usecase.search.SearchMoviesUseCase
import com.master8.shana.domain.usecase.search.SearchPostersByMovieUseCase

class SearchModule(
    private val moviesModule: MoviesModule
) {
    val searchByMovieUseCase by lazy {
        SearchByMovieUseCase(
            searchMoviesUseCase
        )
    }
    val searchMoviesUseCase by lazy {
        SearchMoviesUseCase(
            searchRepository
        )
    }

    val searchPostersByMovieUseCase by lazy {
        SearchPostersByMovieUseCase(
            searchRepository
        )
    }

    private val searchRepository by lazy {
        SearchRepositoryImpl(
            createTMDbApiService(),
            moviesModule.firebaseRealtimeDatabase
        )
    }
}