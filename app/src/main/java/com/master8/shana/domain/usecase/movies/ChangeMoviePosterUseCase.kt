package com.master8.shana.domain.usecase.movies

import android.net.Uri
import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.entity.WatchStatus
import com.master8.shana.domain.repository.MoviesRepository

class ChangeMoviePosterUseCase(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(movie: Movie, poster: Uri): Movie {
        val updatedMovie = movie.copy(poster = poster)

        if (updatedMovie.watchStatus == WatchStatus.WATCHED) {
            moviesRepository.updateGoodMovie(updatedMovie)
        } else {
            moviesRepository.updateNeedToWatchMovie(updatedMovie)
        }

        return updatedMovie
    }
}