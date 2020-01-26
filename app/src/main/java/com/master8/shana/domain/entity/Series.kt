package com.master8.shana.domain.entity

import android.net.Uri

data class Series(
    val name: String,
    val originalName: String,
    val releaseYear: Int,
    val poster: Uri?,

    val internalId: Long?,
    val externalId: Int?
)