package com.master8.shana.domain.usecase

import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.entity.WatchStatus
import com.master8.shana.domain.repository.MoviesRepository
import java.util.*

class AddGoodMovieUseCase(
    private val moviesRepository: MoviesRepository,
    private val prepareMovieToAddUseCase: PrepareMovieToAddUseCase
) {
    suspend operator fun invoke(movie: Movie) {
        val preparedMovie = prepareMovieToAddUseCase(movie, WatchStatus.WATCHED)
        moviesRepository.addGoodMovie(preparedMovie)
    }
}