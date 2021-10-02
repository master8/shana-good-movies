package com.master8.shana.domain.entity

import java.util.*

data class Movie(
    val name: String,
    val originalName: String,
    val releaseYear: Int,
    val poster: Image? = null,

    val movieType: MovieType = MovieType.UNKNOWN,
    val watchStatus: WatchStatus = WatchStatus.UNKNOWN,
    val saveStatus: SaveStatus = SaveStatus.UNKNOWN,
    val releaseStatus: ReleaseStatus = ReleaseStatus.UNKNOWN,

    val externalId: Int? = null,
    val internalId: UUID? = null,
    val dateAdded: Long? = null,

    val relatedSeries: Series? = null,
    val episodeCount: Int? = null,
    val seasonNumber: Int? = null
)