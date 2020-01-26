package com.master8.shana.domain.usecase

import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.repository.MoviesRepository

class AddGoodMovieUseCase(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(movie: Movie) = moviesRepository.addGoodMovie(movie)
}