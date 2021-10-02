package com.master8.shana.domain.usecase.movies

import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.entity.ReleaseStatus
import com.master8.shana.domain.entity.WatchStatus
import com.master8.shana.domain.repository.MoviesRepository

class ChangeReleaseStatusUseCase(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(movie: Movie, newReleaseStatus: ReleaseStatus): Movie {
        val updatedMovie = movie.copy(releaseStatus = newReleaseStatus)

        if (updatedMovie.watchStatus == WatchStatus.WATCHED) {
            check(movie.releaseStatus == ReleaseStatus.READY)
            moviesRepository.updateGoodMovie(updatedMovie)
        } else {
            moviesRepository.updateNeedToWatchMovie(updatedMovie)
        }

        return updatedMovie
    }
}