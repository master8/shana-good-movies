package com.master8.shana.data.source.firebase.database

import com.google.firebase.database.Query
import com.master8.shana.data.source.firebase.database.dto.FirebaseMovieDto
import com.master8.shana.data.source.firebase.database.dto.FirebaseSeriesDto

interface FirebaseRealtimeDatabase {

    val goodMovies: Query
    val needToWatchMovies: Query

    fun putGoodMovie(movie: FirebaseMovieDto)
    fun putNeedToWatchMovie(movie: FirebaseMovieDto)

    fun putSeries(series: FirebaseSeriesDto)

    suspend fun getAllMovies(): List<FirebaseMovieDto>
    suspend fun getAllSeries(): List<FirebaseSeriesDto>

    suspend fun getGoodMovies(): List<FirebaseMovieDto>

    fun removeGoodMovie(movie: FirebaseMovieDto)
    fun removeNeedToWatchMovie(movie: FirebaseMovieDto)
}