package com.master8.shana.ui.search

import androidx.lifecycle.*
import com.master8.shana.domain.entity.ChangedMovie
import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.usecase.GetChangedMovieUseCase
import com.master8.shana.domain.usecase.SearchMoviesUseCase
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    getChangedMovieUseCase: GetChangedMovieUseCase
) : ViewModel() {

    private val _searchResults = MutableLiveData<List<Movie>>()
    val searchResults: LiveData<List<Movie>> = _searchResults

    private val _isSearching = MutableLiveData<Boolean>()
    val isSearching: LiveData<Boolean> = _isSearching

    private val changedMovie = getChangedMovieUseCase()
    private val movieChangesObserver = Observer<ChangedMovie>() { replaceMovie(it) }

    init {
        changedMovie.observeForever(movieChangesObserver)
    }

    fun search(query: String) = viewModelScope.launch {
        _searchResults.value = null
        _isSearching.value = true
        if (query.isNotEmpty()) {
            _searchResults.value = searchMoviesUseCase(query)
        }
        _isSearching.value = false
    }

    private fun replaceMovie(changedMovie: ChangedMovie) {
        val index = _searchResults.value?.indexOf(changedMovie.oldMovie) ?: return
        if (index == -1) {
            return
        }

        _searchResults.value?.toMutableList()?.let {
            it[index] = changedMovie.newMovie
            _searchResults.value = it
        }
    }

    override fun onCleared() {
        changedMovie.removeObserver(movieChangesObserver)
    }
}