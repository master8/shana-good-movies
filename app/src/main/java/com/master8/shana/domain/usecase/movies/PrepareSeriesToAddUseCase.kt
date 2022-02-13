package com.master8.shana.domain.usecase.movies

import com.master8.shana.domain.entity.Series
import com.master8.shana.domain.repository.MoviesRepository
import java.util.*

class PrepareSeriesToAddUseCase(
    private val moviesRepository: MoviesRepository
) {

    suspend operator fun invoke(series: Series): Series {
        return prepareSeries(series)
    }

    private suspend fun prepareSeries(series: Series): Series {
        if (!series.isNotAddedBefore())
            return series
        return moviesRepository.getAllSeries().find { it.externalId == series.externalId }
            ?: series.copy(internalId = generateInternalId())
    }

    private fun Series.isNotAddedBefore() = this.internalId == null

    private fun generateInternalId(): UUID = UUID.randomUUID()
}