package com.master8.shana.app.dependency

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.master8.shana.app.app
import com.master8.shana.ui.linkmovie.LinkMoviesViewModel
import com.master8.shana.ui.movies.MovieViewModel
import com.master8.shana.ui.search.SearchViewModel

class ViewModelFactory(
    context: Context
) : ViewModelProvider.Factory {

    private val moviesModule = context.app.moviesModule
    private val searchModule = context.app.searchModule

    private val movieViewModel: MovieViewModel
        get() = MovieViewModel(
            moviesModule.addGoodMovieUseCase,
            moviesModule.addNeedToWatchMovieUseCase
        )

    private val searchViewModel: SearchViewModel
        get() = SearchViewModel(
            searchModule.searchMoviesUseCase,
            moviesModule.getChangedMovieUseCase
        )

    private val linkMovieViewModel: LinkMoviesViewModel
        get() = LinkMoviesViewModel(searchModule.searchByMovieUseCase)

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(SearchViewModel::class.java) -> { searchViewModel }
            isAssignableFrom(MovieViewModel::class.java) -> { movieViewModel }
            isAssignableFrom(LinkMoviesViewModel::class.java) -> { linkMovieViewModel }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T

}