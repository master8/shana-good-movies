package com.master8.shana.ui

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.master8.shana.domain.entity.ChangedMovie
import com.master8.shana.domain.usecase.movies.GetChangedMovieUseCase

abstract class ChangesMovieViewModel(
    getChangedMovieUseCase: GetChangedMovieUseCase
) : ViewModel() {

    private val changedMovie = getChangedMovieUseCase()
    private val movieChangesObserver = Observer<ChangedMovie>() { onMovieChanged(it) }

    init {
        changedMovie.observeForever(movieChangesObserver)
    }

    protected abstract fun onMovieChanged(changedMovie: ChangedMovie)

    override fun onCleared() {
        changedMovie.removeObserver(movieChangesObserver)
    }
}