package com.master8.shana.data.source.firebase.database.dto

import android.net.Uri

data class FirebaseSeriesDto(
    var name: String = "",
    var originalName: String = "",
    var releaseYear: Int = 0,
    var poster: Uri? = null,

    var internalId: String = "",
    var externalId: Int? = null
)