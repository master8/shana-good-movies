package com.master8.shana.data.repository.converters

import com.master8.shana.data.source.tmdb.createTMDbAbsoluteImageUri
import com.master8.shana.data.source.tmdb.dto.MediaDto
import com.master8.shana.data.source.tmdb.dto.SeasonDto
import com.master8.shana.data.source.tmdb.getYearFromTMDbDate
import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.entity.Series


fun MediaDto.toMovie() = Movie(
    name,
    originalName,
    releaseDate?.let { getYearFromTMDbDate(it) } ?: 0,
    createTMDbAbsoluteImageUri(poster),
    externalId = id
)

fun MediaDto.toSeries() = Series(
    name,
    originalName,
    releaseDate?.let { getYearFromTMDbDate(it) } ?: 0,
    createTMDbAbsoluteImageUri(poster),
    externalId = id
)

fun SeasonDto.toMovie(relatedSeries: Series) = Movie(
    name,
    relatedSeries.originalName,
    getYearFromTMDbDate(releaseDate),
    createTMDbAbsoluteImageUri(poster),
    externalId = id,
    relatedSeries = relatedSeries,
    episodeCount = episodeCount,
    seasonNumber = seasonNumber
)