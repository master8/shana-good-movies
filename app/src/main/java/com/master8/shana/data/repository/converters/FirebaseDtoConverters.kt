package com.master8.shana.data.repository.converters

import com.google.firebase.database.DataSnapshot
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.master8.shana.data.source.firebase.database.dto.*
import com.master8.shana.domain.entity.*
import java.util.*

private val storageReference by lazy { Firebase.storage.reference }

fun parseMovie(snapshot: DataSnapshot): Movie = snapshot.getValue(FirebaseMovieDto::class.java)!!.toMovie()

fun buildFirebaseMovieDto(movie: Movie): FirebaseMovieDto =
    with (movie) {
        FirebaseMovieDto(
            name,
            originalName,
            releaseYear,
            poster.toString(),
            poster?.blurHash,
            movieType.toFirebaseConst(),
            watchStatus.toFirebaseConst(),
            saveStatus.toFirebaseConst(),
            releaseStatus.toFirebaseConst(),
            externalId,
            internalId.toString(),
            dateAdded!!,
            relatedSeries?.let {
                buildFirebaseSeriesDto(
                    it
                )
            },
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
            poster?.blurHash,
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
    WatchStatus.UNKNOWN -> WATCH_STATUS_UNKNOWN
    WatchStatus.WATCHED -> WATCH_STATUS_WATCHED
    WatchStatus.NOT_WATCHED -> WATCH_STATUS_NOT_WATCHED
}

private fun SaveStatus.toFirebaseConst() = when (this) {
    SaveStatus.UNKNOWN -> SAVE_STATUS_UNKNOWN
    SaveStatus.SAVED -> SAVE_STATUS_SAVED
    SaveStatus.NOT_SAVED -> SAVE_STATUS_NOT_SAVED
}

private fun ReleaseStatus.toFirebaseConst() = when (this) {
    ReleaseStatus.WAITING -> RELEASE_STATUS_WAITING
    ReleaseStatus.IN_PROGRESS -> RELEASE_STATUS_IN_PROGRESS
    ReleaseStatus.READY -> RELEASE_STATUS_READY
    ReleaseStatus.UNKNOWN -> RELEASE_STATUS_UNKNOWN
}

fun FirebaseMovieDto.toMovie(): Movie = Movie(
    name, originalName, releaseYear,
    poster?.let { buildImage(it, posterBlurHash) },
    movieType.toMovieType(),
    watchStatus.toWatchStatus(),
    saveStatus.toSaveStatus(),
    releaseStatus.toReleaseStatus(),
    externalId,
    UUID.fromString(internalId),
    dateAdded,
    relatedSeries?.toSeries(),
    episodeCount,
    seasonNumber
)

fun FirebaseSeriesDto.toSeries(): Series = Series(
    name, originalName, releaseYear,
    poster?.let { buildImage(it, posterBlurHash) },
    UUID.fromString(internalId),
    externalId
)

private fun buildImage(image: String, blurHash: String?): Image? = if (image.contains("http")) {
//    UriImage(Uri.parse(image))
    null
} else {
    StorageReferenceImage(storageReference.child(image), blurHash)
}

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

private fun Int.toReleaseStatus() = when (this) {
    RELEASE_STATUS_UNKNOWN -> ReleaseStatus.UNKNOWN
    RELEASE_STATUS_READY -> ReleaseStatus.READY
    RELEASE_STATUS_WAITING -> ReleaseStatus.WAITING
    RELEASE_STATUS_IN_PROGRESS -> ReleaseStatus.IN_PROGRESS
    else -> ReleaseStatus.UNKNOWN
}