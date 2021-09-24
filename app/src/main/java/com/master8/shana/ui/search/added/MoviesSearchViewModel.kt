package com.master8.shana.ui.search.added

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.usecase.search.SearchGoodMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MoviesSearchViewModel(
    private val searchGoodMoviesUseCase: SearchGoodMoviesUseCase
) : ViewModel() {

    private val _foundMovies = MutableStateFlow(emptyList<Movie>())
    val foundMovies = _foundMovies.asStateFlow()

    fun search(query: String) {
        viewModelScope.launch {
            _foundMovies.value = searchGoodMoviesUseCase(query)
                .sortedByDescending { it.dateAdded }
        }
    }
}