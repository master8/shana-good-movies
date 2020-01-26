package com.master8.shana.domain.entity

import android.net.Uri

data class Movie(
    val name: String,
    val originalName: String,
    val releaseYear: Int,
    val poster: Uri?,

    val movieType: MovieType,
    val watchStatus: WatchStatus,
    val saveStatus: SaveStatus,

    val externalId: Int?,
    val internalId: Long?,
    val dateAdded: Long?,

    val relatedSeries: Series?
)