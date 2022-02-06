package com.master8.shana.domain.usecase.movies

import com.master8.shana.domain.entity.Series
import com.master8.shana.domain.repository.MoviesRepository

class PrepareSeriesToAddUseCase(
    private val moviesRepository: MoviesRepository
) {

    suspend operator fun invoke(series: Series): Series {
        return prepareSeries(series)
    }

    private suspend fun prepareSeries(series: Series) =
        moviesRepository.getAllSeries().find { it.externalId == series.externalId } ?: series
}