package com.master8.shana.ui.movies.dialog

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.usecase.movies.ChangeMoviePosterUseCase
import com.master8.shana.domain.usecase.movies.DeleteMovieUseCase
import com.master8.shana.domain.usecase.movies.MoveToGoodMoviesUseCase
import com.master8.shana.domain.usecase.search.SearchPostersByMovieUseCase
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

    private val _posters = MutableLiveData<Event<List<Uri>>>()
    val posters: LiveData<Event<List<Uri>>> = _posters

    private val _closeDialog = MutableLiveData<Event<Boolean>>()
    val closeDialog: LiveData<Event<Boolean>> = _closeDialog

    fun selectMovie(movie: Movie) {
        _selectedMove.value = movie
        _posters.value = null
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
            _posters.value = Event(searchPostersByMovieUseCase(it))
        }
    }

    fun changeMoviePoster(poster: Uri) = viewModelScope.launch {
        _selectedMove.value?.let {
//            _selectedMove.value = changeMoviePosterUseCase(it, poster)
            changeMoviePosterUseCase(it, poster)
            _closeDialog.value = Event(true)
        }
    }
}