package com.master8.shana.domain.usecase

import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.entity.Series
import com.master8.shana.domain.entity.WatchStatus
import java.util.*

class PrepareMovieToAddUseCase {

    operator fun invoke(movie: Movie, watchStatus: WatchStatus): Movie {
        val preparedRelatedSeries = movie.relatedSeries?.let { prepareSeries(it) }
        return prepareMovie(movie, watchStatus, preparedRelatedSeries)
    }

    private fun prepareSeries(series: Series) = when {
        series.isNotAddedBefore() -> series.copy(internalId = generateInternalId())
        else -> series
    }

    private fun prepareMovie(movie: Movie, watchStatus: WatchStatus, relatedSeries: Series?) = when {
        movie.isNotAddedBefore() -> movie.copy(
            watchStatus = watchStatus,
            internalId = generateInternalId(),
            relatedSeries = relatedSeries,
            dateAdded = generateDateAdded()
        )
        movie.inNotStatus(watchStatus) -> movie.copy(
            watchStatus = watchStatus,
            relatedSeries = relatedSeries
        )
        else -> movie
    }

    private fun Series.isNotAddedBefore() = this.internalId == null
    private fun Movie.isNotAddedBefore() = this.internalId == null
    private fun Movie.inNotStatus(watchStatus: WatchStatus) = this.watchStatus == watchStatus

    private fun generateInternalId(): UUID = UUID.randomUUID()
    private fun generateDateAdded(): Long = System.currentTimeMillis()
}