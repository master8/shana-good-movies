package com.master8.shana.ui.movies.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.master8.shana.domain.entity.Image
import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.entity.ReleaseStatus
import com.master8.shana.domain.entity.SaveStatus
import com.master8.shana.domain.usecase.movies.*
import com.master8.shana.domain.usecase.search.SearchNamesForMovieUseCase
import com.master8.shana.domain.usecase.search.SearchPostersByMovieUseCase
import com.master8.shana.ui.Event
import kotlinx.coroutines.launch

class MovieDialogViewModel(
    private val deleteMovieUseCase: DeleteMovieUseCase,
    private val moveToGoodMoviesUseCase: MoveToGoodMoviesUseCase,
    private val changeMoviePosterUseCase: ChangeMoviePosterUseCase,
    private val searchPostersByMovieUseCase: SearchPostersByMovieUseCase,
    private val searchNamesForMovieUseCase: SearchNamesForMovieUseCase,
    private val changeMovieNameUseCase: ChangeMovieNameUseCase,
    private val changeReleaseStatusUseCase: ChangeReleaseStatusUseCase,
    private val changeSavedStatusUseCase: ChangeSavedStatusUseCase
) : ViewModel() {

    private val _selectedMove = MutableLiveData<Movie>()
    val selectedMovie: LiveData<Movie> = _selectedMove

    private val _posters = MutableLiveData<Event<List<Image>>>()
    val posters: LiveData<Event<List<Image>>> = _posters

    private val _names = MutableLiveData<Event<List<String>>>()
    val names: LiveData<Event<List<String>>> = _names

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

    fun changeMoviePoster(poster: Image) = viewModelScope.launch {
        _selectedMove.value?.let {
//            _selectedMove.value = changeMoviePosterUseCase(it, poster)
            changeMoviePosterUseCase(it, poster)
            _closeDialog.value = Event(true)
        }
    }

    fun searchNames(movie: Movie) = viewModelScope.launch {
        _names.value = Event(searchNamesForMovieUseCase(movie))
    }

    fun changeName(name: String) = viewModelScope.launch {
        _selectedMove.value?.let {
            _selectedMove.value = changeMovieNameUseCase(it, name)
        }
    }

    fun markSaved(movie: Movie) = viewModelScope.launch {
        changeSavedStatusUseCase(movie, SaveStatus.SAVED)
        _closeDialog.value = Event(true)
    }

    fun markNotSaved(movie: Movie) = viewModelScope.launch {
        changeSavedStatusUseCase(movie, SaveStatus.NOT_SAVED)
        _closeDialog.value = Event(true)
    }

    fun markReadyToWatch(movie: Movie) = viewModelScope.launch {
        changeReleaseStatusUseCase(movie, ReleaseStatus.READY)
        _closeDialog.value = Event(true)
    }

    fun markWaitingRelease(movie: Movie) = viewModelScope.launch {
        changeReleaseStatusUseCase(movie, ReleaseStatus.WAITING)
        _closeDialog.value = Event(true)
    }

    fun markReleaseInProgress(movie: Movie) = viewModelScope.launch {
        changeReleaseStatusUseCase(movie, ReleaseStatus.IN_PROGRESS)
        _closeDialog.value = Event(true)
    }
}