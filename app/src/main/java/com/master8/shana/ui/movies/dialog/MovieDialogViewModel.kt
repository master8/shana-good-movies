package com.master8.shana.ui.movies.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.master8.shana.domain.entity.Movie

class MovieDialogViewModel : ViewModel() {

    private val _selectedMove = MutableLiveData<Movie>()
    val selectedMovie: LiveData<Movie> = _selectedMove

    fun selectMovie(movie: Movie) {
        _selectedMove.value = movie
    }
}