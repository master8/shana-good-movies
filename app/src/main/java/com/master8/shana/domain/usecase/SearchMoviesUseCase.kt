package com.master8.shana.domain.usecase

import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.repository.MoviesRepository

class SearchMoviesUseCase(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(query: String): List<Movie> = moviesRepository.searchMovies(query)
}