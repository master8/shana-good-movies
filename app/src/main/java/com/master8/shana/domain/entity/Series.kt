package com.master8.shana.domain.entity

import android.net.Uri
import java.util.*

data class Series(
    val name: String,
    val originalName: String,
    val releaseYear: Int,
    val poster: Uri? = null,

    val internalId: UUID? = null,
    val externalId: Int? = null
)