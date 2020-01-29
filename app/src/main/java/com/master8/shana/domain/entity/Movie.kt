package com.master8.shana.domain.entity

import android.net.Uri

data class Movie(
    val name: String,
    val originalName: String,
    val releaseYear: Int,
    val poster: Uri? = null,

    val movieType: MovieType = MovieType.UNKNOWN,
    val watchStatus: WatchStatus = WatchStatus.UNKNOWN,
    val saveStatus: SaveStatus = SaveStatus.UNKNOWN,

    val externalId: Int? = null,
    val internalId: Long? = null,
    val dateAdded: Long? = null,

    val relatedSeries: Series? = null,
    val episodeCount: Int = 1,
    val seasonNumber: Int = 1
)