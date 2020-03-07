package com.master8.shana.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.usecase.AddGoodMovieUseCase
import com.master8.shana.domain.usecase.AddNeedToWatchMovieUseCase
import com.master8.shana.domain.usecase.SearchByMovieUseCase
import com.master8.shana.ui.Event
import kotlinx.coroutines.launch

class MovieViewModel(
    private val addGoodMovieUseCase: AddGoodMovieUseCase,
    private val addNeedToWatchMovieUseCase: AddNeedToWatchMovieUseCase
) : ViewModel() {

    private val _onLinkMovie = MutableLiveData<Event<Movie>>()
    val onLinkMovie: LiveData<Event<Movie>> = _onLinkMovie

    private val _openMovieDialog = MutableLiveData<Event<Movie>>()
    val openMovieDialog: LiveData<Event<Movie>> = _openMovieDialog

    fun addGoodMovie(movie: Movie) = viewModelScope.launch {
        addGoodMovieUseCase(movie)
    }

    fun addNeedToWatchMovie(movie: Movie) = viewModelScope.launch {
        addNeedToWatchMovieUseCase(movie)
    }

    fun linkMovie(movie: Movie) = viewModelScope.launch {
        _onLinkMovie.value = Event(movie)
    }

    fun openMovieDialog(movie: Movie) {
        _openMovieDialog.value = Event(movie)
    }
}