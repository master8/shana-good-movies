package com.master8.shana.domain.usecase

import com.master8.shana.domain.entity.Series
import java.util.*

class PrepareSeriesToAddUseCase {

    operator fun invoke(series: Series): Series {
        return prepareSeries(series)
    }

    private fun prepareSeries(series: Series) = when {
        series.isNotAddedBefore() -> series.copy(internalId = generateInternalId())
        else -> series
    }

    private fun Series.isNotAddedBefore() = this.internalId == null

    private fun generateInternalId(): UUID = UUID.randomUUID()
}