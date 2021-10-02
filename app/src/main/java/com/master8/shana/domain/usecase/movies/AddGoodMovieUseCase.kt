package com.master8.shana.domain.usecase.movies

import com.master8.shana.domain.entity.ChangedMovie
import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.entity.ReleaseStatus
import com.master8.shana.domain.entity.WatchStatus
import com.master8.shana.domain.repository.MovieChangesRepository
import com.master8.shana.domain.repository.MoviesRepository

class AddGoodMovieUseCase(
    private val moviesRepository: MoviesRepository,
    private val movieChangesRepository: MovieChangesRepository,
    private val prepareMovieToAddUseCase: PrepareMovieToAddUseCase
) {
    suspend operator fun invoke(movie: Movie) {
        val preparedMovie = prepareMovieToAddUseCase(
            movie.copy(releaseStatus = ReleaseStatus.READY),
            WatchStatus.WATCHED
        )
        moviesRepository.addGoodMovie(preparedMovie)
        movieChangesRepository.movieWasChanged(ChangedMovie(movie, preparedMovie))
    }
}