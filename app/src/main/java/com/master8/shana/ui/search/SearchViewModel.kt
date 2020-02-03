package com.master8.shana.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.usecase.SearchMoviesUseCase
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    private val _searchResults = MutableLiveData<List<Movie>>()
    val searchResults: LiveData<List<Movie>> = _searchResults

    private val _isSearching = MutableLiveData<Boolean>()
    val isSearching: LiveData<Boolean> = _isSearching

    fun search(query: String) = viewModelScope.launch {
        _isSearching.value = true
        _searchResults.value = searchMoviesUseCase(query)
        _isSearching.value = false
    }
}