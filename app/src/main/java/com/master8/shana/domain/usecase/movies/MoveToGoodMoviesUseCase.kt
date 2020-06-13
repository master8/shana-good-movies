package com.master8.shana.domain.usecase.movies

import com.master8.shana.domain.entity.ChangedMovie
import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.entity.WatchStatus
import com.master8.shana.domain.repository.MovieChangesRepository
import com.master8.shana.domain.repository.MoviesRepository

class MoveToGoodMoviesUseCase(
    private val moviesRepository: MoviesRepository,
    private val movieChangesRepository: MovieChangesRepository
) {

    suspend operator fun invoke(movie: Movie) {
        moviesRepository.deleteNeedToWatchMovie(movie)
        val updatedMovie = movie.copy(
            watchStatus = WatchStatus.WATCHED,
            dateAdded = generateDateAdded()
        )
        moviesRepository.addGoodMovie(updatedMovie)
        movieChangesRepository.movieWasChanged(ChangedMovie(movie, updatedMovie))
    }

    private fun generateDateAdded(): Long = System.currentTimeMillis()
}