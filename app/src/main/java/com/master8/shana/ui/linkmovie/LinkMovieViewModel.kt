package com.master8.shana.ui.linkmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.usecase.SearchByMovieUseCase
import kotlinx.coroutines.launch

class LinkMovieViewModel(
    private val searchByMovieUseCase: SearchByMovieUseCase
): ViewModel() {

    private val _originalMovie = MutableLiveData<Movie>()
    val originalMovie: LiveData<Movie> = _originalMovie

    private val _searchResults = MutableLiveData<List<Movie>>()
    val searchResults: LiveData<List<Movie>> = _searchResults

    fun searchByMovie(movie: Movie) = viewModelScope.launch {
        _searchResults.value = null
        _originalMovie.value = movie
        _searchResults.value = searchByMovieUseCase(movie)
    }

    fun linkWithMovie(selectedMovie: Movie) {
        originalMovie.value?.let {
            //TODO
        }
    }
}