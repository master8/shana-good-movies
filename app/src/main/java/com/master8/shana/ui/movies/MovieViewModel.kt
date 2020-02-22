package com.master8.shana.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.usecase.AddGoodMovieUseCase
import com.master8.shana.domain.usecase.AddNeedToWatchMovieUseCase
import com.master8.shana.domain.usecase.SearchByMovieUseCase
import kotlinx.coroutines.launch

class MovieViewModel(
    private val addGoodMovieUseCase: AddGoodMovieUseCase,
    private val addNeedToWatchMovieUseCase: AddNeedToWatchMovieUseCase
) : ViewModel() {

    private val _onLinkMovie = MutableLiveData<Movie>()
    val onLinkMovie: LiveData<Movie> = _onLinkMovie

    fun addGoodMovie(movie: Movie) = viewModelScope.launch {
        addGoodMovieUseCase(movie)
    }

    fun addNeedToWatchMovie(movie: Movie) = viewModelScope.launch {
        addNeedToWatchMovieUseCase(movie)
    }

    fun linkMovie(movie: Movie) = viewModelScope.launch {
        _onLinkMovie.value = movie
    }
}