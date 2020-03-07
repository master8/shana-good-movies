package com.master8.shana.domain.usecase

import com.master8.shana.domain.entity.ChangedMovie
import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.entity.WatchStatus
import com.master8.shana.domain.repository.MoviesRepository

class MoveToGoodMoviesUseCase(
    private val moviesRepository: MoviesRepository
) {

    suspend operator fun invoke(movie: Movie) {
        moviesRepository.deleteNeedToWatchMovie(movie)
        val updatedMovie = movie.copy(
            watchStatus = WatchStatus.WATCHED,
            dateAdded = generateDateAdded()
        )
        moviesRepository.addGoodMovie(updatedMovie)
        moviesRepository.movieWasChanged(ChangedMovie(movie, updatedMovie))
    }

    private fun generateDateAdded(): Long = System.currentTimeMillis()
}