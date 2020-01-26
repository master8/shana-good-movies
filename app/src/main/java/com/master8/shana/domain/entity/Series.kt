package com.master8.shana.domain.entity

import android.net.Uri

data class Series(
    val name: String,
    val originalName: String,
    val releaseYear: Int,
    val poster: Uri? = null,

    val internalId: Long? = null,
    val externalId: Int? = null
)