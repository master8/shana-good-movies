package com.master8.shana.domain.usecase.search

import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.repository.SearchRepository

class SearchGoodMoviesUseCase(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(query: String): List<Movie> = repository.searchGoodMovies(query)
}