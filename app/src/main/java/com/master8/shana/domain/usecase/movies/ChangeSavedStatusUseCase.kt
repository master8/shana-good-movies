package com.master8.shana.domain.usecase.movies

import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.entity.ReleaseStatus
import com.master8.shana.domain.entity.SaveStatus
import com.master8.shana.domain.entity.WatchStatus
import com.master8.shana.domain.repository.MoviesRepository

class ChangeSavedStatusUseCase(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(movie: Movie, newSavedStatus: SaveStatus): Movie {
        val updatedMovie = movie.copy(saveStatus = newSavedStatus)

        if (updatedMovie.watchStatus == WatchStatus.WATCHED) {
            moviesRepository.updateGoodMovie(updatedMovie)
        } else {
            moviesRepository.updateNeedToWatchMovie(updatedMovie)
        }

        return updatedMovie
    }
}