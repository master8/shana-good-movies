package com.master8.shana.domain.usecase.movies

import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.entity.WatchStatus
import com.master8.shana.domain.repository.MoviesRepository

class ChangeMovieNameUseCase(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(movie: Movie, name: String): Movie {
        val updatedMovie = movie.copy(name = name)

        if (updatedMovie.watchStatus == WatchStatus.WATCHED) {
            moviesRepository.updateGoodMovie(updatedMovie)
        } else {
            moviesRepository.updateNeedToWatchMovie(updatedMovie)
        }

        return updatedMovie
    }
}