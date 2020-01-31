package com.master8.shana.data.repository

import com.master8.shana.data.source.firebase.database.dto.*
import com.master8.shana.domain.entity.*

fun buildFirebaseMovieDto(movie: Movie): FirebaseMovieDto =
    with (movie) {
        FirebaseMovieDto(
            name,
            originalName,
            releaseYear,
            poster.toString(),
            movieType.toFirebaseConst(),
            watchStatus.toFirebaseConst(),
            saveStatus.toFirebaseConst(),
            externalId,
            internalId.toString(),
            dateAdded!!,
            relatedSeries?.let { buildFirebaseSeriesDto(it) },
            episodeCount,
            seasonNumber
        )
    }

fun buildFirebaseSeriesDto(series: Series): FirebaseSeriesDto =
    with (series) {
        FirebaseSeriesDto(
            name,
            originalName,
            releaseYear,
            poster.toString(),
            internalId.toString(),
            externalId
        )
    }

private fun MovieType.toFirebaseConst() = when (this) {
    MovieType.UNKNOWN -> MEDIA_TYPE_UNKNOWN
    MovieType.ANIME -> MEDIA_TYPE_ANIME
    MovieType.MOVIE -> MEDIA_TYPE_MOVIE
    MovieType.CARTOON -> MEDIA_TYPE_CARTOON
}

private fun WatchStatus.toFirebaseConst() = when (this) {
    WatchStatus.UNKNOWN -> WATCH_STATUS_WATCHED
    WatchStatus.WATCHED -> WATCH_STATUS_NOT_WATCHED
    WatchStatus.NOT_WATCHED -> WATCH_STATUS_UNKNOWN
}

private fun SaveStatus.toFirebaseConst() = when (this) {
    SaveStatus.UNKNOWN -> SAVE_STATUS_UNKNOWN
    SaveStatus.SAVED -> SAVE_STATUS_SAVED
    SaveStatus.NOT_SAVED -> SAVE_STATUS_NOT_SAVED
}