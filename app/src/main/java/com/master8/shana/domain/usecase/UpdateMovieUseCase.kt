package com.master8.shana.domain.usecase

import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.entity.WatchStatus
import com.master8.shana.domain.repository.MoviesRepository

class UpdateMovieUseCase(
    private val moviesRepository: MoviesRepository,
    private val prepareSeriesToAddUseCase: PrepareSeriesToAddUseCase
) {
    suspend operator fun invoke(oldMovie: Movie, newMovie: Movie) {
        val updatedMovie = oldMovie.copy(
            poster = newMovie.poster,
            externalId = newMovie.externalId,
            relatedSeries = newMovie.relatedSeries?.let { prepareSeriesToAddUseCase(it) },
            episodeCount = newMovie.episodeCount,
            seasonNumber = newMovie.seasonNumber
        )

        if (updatedMovie.watchStatus == WatchStatus.WATCHED) {
            moviesRepository.updateGoodMovie(updatedMovie)
        } else {
            moviesRepository.updateNeedToWatchMovie(updatedMovie)
        }
    }
}