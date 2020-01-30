package com.master8.shana.data.source.firebase.database

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.master8.shana.data.source.firebase.database.dto.FirebaseMovieDto
import com.master8.shana.data.source.firebase.database.dto.FirebaseSeriesDto

class FirebaseRealtimeDatabaseImpl : FirebaseRealtimeDatabase {

    private val database by lazy { Firebase.database.reference }

    override fun addGoodMovie(movie: FirebaseMovieDto) {
        database
            .child(PATH_GOOD_MOVIES)
            .child(movie.internalId)
            .setValue(movie)
    }

    override fun addNeedToWatchMovie(movie: FirebaseMovieDto) {
        database
            .child(PATH_NEED_TO_WATCH_MOVIES)
            .child(movie.internalId)
            .setValue(movie)
    }

    override fun addSeries(series: FirebaseSeriesDto) {
        database
            .child(PATH_SERIES)
            .child(series.internalId)
            .setValue(series)
    }

    companion object {
        const val PATH_GOOD_MOVIES = "goodMovies"
        const val PATH_NEED_TO_WATCH_MOVIES = "needToWatchMovies"
        const val PATH_SERIES = "series"
    }
}