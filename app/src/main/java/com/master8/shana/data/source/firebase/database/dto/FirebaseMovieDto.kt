package com.master8.shana.data.source.firebase.database.dto

import android.net.Uri

data class FirebaseMovieDto(
    var name: String = "",
    var originalName: String = "",
    var releaseYear: Int = 0,
    var poster: Uri? = null,

    var movieType: Int = MEDIA_TYPE_UNKNOWN,
    var watchStatus: Int = WATCH_STATUS_UNKNOWN,
    var saveStatus: Int = SAVE_STATUS_UNKNOWN,

    var externalId: Int? = null,
    var internalId: String = "",
    var dataAdded: Long = 0,

    var relatedSeries: FirebaseSeriesDto? = null,
    var episodeCount: Int? = null,
    var seasonNumber: Int? = null
)