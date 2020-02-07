package com.master8.shana.domain.usecase

import com.master8.shana.domain.entity.ChangedMovie
import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.entity.WatchStatus
import com.master8.shana.domain.repository.MoviesRepository

class AddNeedToWatchMovieUseCase(
    private val moviesRepository: MoviesRepository,
    private val prepareMovieToAddUseCase: PrepareMovieToAddUseCase
) {
    suspend operator fun invoke(movie: Movie) {
        val preparedMovie = prepareMovieToAddUseCase(movie, WatchStatus.NOT_WATCHED)
        moviesRepository.addNeedToWatchMovie(preparedMovie)
        moviesRepository.movieWasChanged(ChangedMovie(movie, preparedMovie))
    }
}