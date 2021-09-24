package com.master8.shana.app.dependency

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.master8.shana.app.app
import com.master8.shana.ui.linkmovie.LinkMovieViewModel
import com.master8.shana.ui.movies.MovieViewModel
import com.master8.shana.ui.movies.dialog.MovieDialogViewModel
import com.master8.shana.ui.search.SearchViewModel
import com.master8.shana.ui.search.added.MoviesSearchViewModel

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

    private val linkMovieViewModel: LinkMovieViewModel
        get() = LinkMovieViewModel(
            searchModule.searchByMovieUseCase,
            moviesModule.updateMovieUseCase,
            moviesModule.getChangedMovieUseCase
        )

    private val movieDialogViewModel: MovieDialogViewModel
        get() = MovieDialogViewModel(
            moviesModule.deleteMovieUseCase,
            moviesModule.moveToWatchMovieUseCase,
            moviesModule.changeMoviePosterUseCase,
            searchModule.searchPostersByMovieUseCase,
            searchModule.searchNamesForMovieUseCase,
            moviesModule.changeMovieNameUseCase
        )

    private val moviesSearchViewModel: MoviesSearchViewModel
        get() = MoviesSearchViewModel(
            searchModule.searchGoodMoviesUseCase
        )

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(SearchViewModel::class.java) -> { searchViewModel }
            isAssignableFrom(MovieViewModel::class.java) -> { movieViewModel }
            isAssignableFrom(LinkMovieViewModel::class.java) -> { linkMovieViewModel }
            isAssignableFrom(MovieDialogViewModel::class.java) -> { movieDialogViewModel }
            isAssignableFrom(MoviesSearchViewModel::class.java) -> { moviesSearchViewModel }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T

}