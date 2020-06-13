package com.master8.shana.domain.usecase.movies

import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.entity.WatchStatus
import com.master8.shana.domain.repository.MoviesRepository

class LinkMovieUseCase(
    private val moviesRepository: MoviesRepository,
    private val prepareSeriesToAddUseCase: PrepareSeriesToAddUseCase
) {
    suspend operator fun invoke(oldMovie: Movie, newMovie: Movie) {
        val updatedMovie = oldMovie.copy(
            originalName = newMovie.originalName,
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