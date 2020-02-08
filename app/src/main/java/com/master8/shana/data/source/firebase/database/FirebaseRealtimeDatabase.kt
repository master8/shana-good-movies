package com.master8.shana.data.source.firebase.database

import com.google.firebase.database.Query
import com.master8.shana.data.source.firebase.database.dto.FirebaseMovieDto
import com.master8.shana.data.source.firebase.database.dto.FirebaseSeriesDto

interface FirebaseRealtimeDatabase {

    val goodMovies: Query
    val needToWatchMovies: Query

    fun addGoodMovie(movie: FirebaseMovieDto)
    fun addNeedToWatchMovie(movie: FirebaseMovieDto)

    fun addSeries(series: FirebaseSeriesDto)

    suspend fun getAllMovies(): List<FirebaseMovieDto>
}