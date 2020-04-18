package com.master8.shana.ui.movies.dialog

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.usecase.ChangeMoviePosterUseCase
import com.master8.shana.domain.usecase.DeleteMovieUseCase
import com.master8.shana.domain.usecase.MoveToGoodMoviesUseCase
import com.master8.shana.domain.usecase.SearchPostersByMovieUseCase
import com.master8.shana.ui.Event
import kotlinx.coroutines.launch

class MovieDialogViewModel(
    private val deleteMovieUseCase: DeleteMovieUseCase,
    private val moveToGoodMoviesUseCase: MoveToGoodMoviesUseCase,
    private val changeMoviePosterUseCase: ChangeMoviePosterUseCase,
    private val searchPostersByMovieUseCase: SearchPostersByMovieUseCase
) : ViewModel() {

    private val _selectedMove = MutableLiveData<Movie>()
    val selectedMovie: LiveData<Movie> = _selectedMove

    private val _posters = MutableLiveData<List<Uri>>()
    val posters: LiveData<List<Uri>> = _posters

    private val _closeDialog = MutableLiveData<Event<Boolean>>()
    val closeDialog: LiveData<Event<Boolean>> = _closeDialog

    fun selectMovie(movie: Movie) {
        _selectedMove.value = movie
    }

    fun deleteMovie(movie: Movie) = viewModelScope.launch {
        deleteMovieUseCase(movie)
        _closeDialog.value = Event(true)
    }

    fun moveToGoodMovies(movie: Movie) = viewModelScope.launch {
        moveToGoodMoviesUseCase(movie)
        _closeDialog.value = Event(true)
    }

    fun searchPosters() = viewModelScope.launch {
        selectedMovie.value?.let {
            _posters.value = searchPostersByMovieUseCase(it)
        }
    }

    fun changeMoviePoster(poster: Uri) = viewModelScope.launch {
        _selectedMove.value?.let {
            _selectedMove.value = changeMoviePosterUseCase(it, poster)
        }
    }
}