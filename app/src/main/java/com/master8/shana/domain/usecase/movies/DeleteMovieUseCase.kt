package com.master8.shana.domain.usecase.movies

import com.master8.shana.domain.entity.ChangedMovie
import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.entity.WatchStatus
import com.master8.shana.domain.repository.MovieChangesRepository
import com.master8.shana.domain.repository.MoviesRepository

class DeleteMovieUseCase(
    private val moviesRepository: MoviesRepository,
    private val movieChangesRepository: MovieChangesRepository
) {

    suspend operator fun invoke(movie: Movie) {

        when (movie.watchStatus) {
            WatchStatus.WATCHED -> moviesRepository.deleteGoodMovie(movie)
            WatchStatus.NOT_WATCHED -> moviesRepository.deleteNeedToWatchMovie(movie)
            else -> return
        }

        val deletedMovie = movie.copy(
            internalId = null,
            watchStatus = WatchStatus.UNKNOWN,
            dateAdded = null
        )
        movieChangesRepository.movieWasChanged(ChangedMovie(movie, deletedMovie))
    }
}