package com.master8.shana.data.repository

import android.net.Uri
import com.master8.shana.data.source.firebase.database.dto.*
import com.master8.shana.domain.entity.*
import java.util.*

fun FirebaseMovieDto.toMovie(): Movie = Movie(
    name, originalName, releaseYear,
    poster?.let { Uri.parse(it) },
    movieType.toMovieType(),
    watchStatus.toWatchStatus(),
    saveStatus.toSaveStatus(),
    externalId,
    UUID.fromString(internalId),
    dataAdded,
    relatedSeries?.toSeries(),
    episodeCount,
    seasonNumber
)

fun FirebaseSeriesDto.toSeries(): Series = Series(
    name, originalName, releaseYear,
    poster?.let { Uri.parse(it) },
    UUID.fromString(internalId),
    externalId
)

private fun Int.toMovieType() = when (this) {
    MEDIA_TYPE_ANIME -> MovieType.ANIME
    MEDIA_TYPE_MOVIE -> MovieType.MOVIE
    MEDIA_TYPE_CARTOON -> MovieType.CARTOON
    else -> MovieType.UNKNOWN
}

private fun Int.toWatchStatus() = when (this) {
    WATCH_STATUS_WATCHED -> WatchStatus.WATCHED
    WATCH_STATUS_NOT_WATCHED -> WatchStatus.NOT_WATCHED
    else -> WatchStatus.UNKNOWN
}

private fun Int.toSaveStatus() = when (this) {
    SAVE_STATUS_SAVED -> SaveStatus.SAVED
    SAVE_STATUS_NOT_SAVED -> SaveStatus.NOT_SAVED
    else -> SaveStatus.UNKNOWN
}