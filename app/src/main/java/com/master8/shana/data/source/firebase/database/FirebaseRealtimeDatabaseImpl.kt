package com.master8.shana.data.source.firebase.database

import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.master8.shana.data.source.firebase.database.dto.FirebaseMovieDto
import com.master8.shana.data.source.firebase.database.dto.FirebaseSeriesDto
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseRealtimeDatabaseImpl : FirebaseRealtimeDatabase {

    private val database by lazy { Firebase.database.reference }

    override val goodMovies: DatabaseReference by lazy { database.child(PATH_GOOD_MOVIES) }
    override val needToWatchMovies: DatabaseReference by lazy { database.child(PATH_NEED_TO_WATCH_MOVIES) }

    override fun addGoodMovie(movie: FirebaseMovieDto) {
        goodMovies
            .child(movie.internalId)
            .setValue(movie)
    }

    override fun addNeedToWatchMovie(movie: FirebaseMovieDto) {
        needToWatchMovies
            .child(movie.internalId)
            .setValue(movie)
    }

    override fun addSeries(series: FirebaseSeriesDto) {
        database
            .child(PATH_SERIES)
            .child(series.internalId)
            .setValue(series)
    }

    override suspend fun getAllMovies(): List<FirebaseMovieDto> {
        return goodMovies.getMovies() + needToWatchMovies.getMovies()
    }

    private suspend fun DatabaseReference.getMovies(): List<FirebaseMovieDto> = suspendCoroutine { continuation ->
        this.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaesError: DatabaseError) {
                continuation.resumeWithException(RuntimeException("Query was cancelled! ${databaesError.message}"))
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                continuation.resume(
                    dataSnapshot
                        .children
                        .map { it.getValue(FirebaseMovieDto::class.java)!! }
                )

            }

        })
    }

    private companion object {
        const val PATH_GOOD_MOVIES = "goodMovies"
        const val PATH_NEED_TO_WATCH_MOVIES = "needToWatchMovies"
        const val PATH_SERIES = "series"
    }
}