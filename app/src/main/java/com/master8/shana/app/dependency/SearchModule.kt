package com.master8.shana.app.dependency

import com.master8.shana.domain.usecase.SearchMoviesUseCase
import com.master8.shana.ui.search.SearchViewModel

class SearchModule(
    private val moviesModule: MoviesModule
) {
    private val searchMoviesUseCase by lazy { SearchMoviesUseCase(moviesModule.moviesRepository) }

    val searchViewModel: SearchViewModel
        get() = SearchViewModel(searchMoviesUseCase, moviesModule.getChangedMovieUseCase)
}