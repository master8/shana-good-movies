package com.master8.shana.data.source.firebase.database

import com.master8.shana.data.source.firebase.database.dto.FirebaseMovieDto
import com.master8.shana.data.source.firebase.database.dto.FirebaseSeriesDto

interface FirebaseRealtimeDatabase {

    fun addGoodMovie(movie: FirebaseMovieDto)
    fun addNeedToWatchMovie(movie: FirebaseMovieDto)

    fun addSeries(series: FirebaseSeriesDto)
}