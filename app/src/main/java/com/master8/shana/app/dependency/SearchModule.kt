package com.master8.shana.app.dependency

import com.master8.shana.domain.usecase.SearchByMovieUseCase
import com.master8.shana.domain.usecase.SearchMoviesUseCase
import com.master8.shana.ui.search.SearchViewModel

class SearchModule(
    private val moviesModule: MoviesModule
) {
    val searchByMovieUseCase by lazy { SearchByMovieUseCase(searchMoviesUseCase) }
    val searchMoviesUseCase by lazy { SearchMoviesUseCase(moviesModule.moviesRepository) }
}