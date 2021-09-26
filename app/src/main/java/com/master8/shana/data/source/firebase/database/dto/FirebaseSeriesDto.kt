package com.master8.shana.data.source.firebase.database.dto

data class FirebaseSeriesDto(
    var name: String = "",
    var originalName: String = "",
    var releaseYear: Int = 0,
    var poster: String? = null,
    var posterBlurHash: String? = null,

    var internalId: String = "",
    var externalId: Int? = null
)