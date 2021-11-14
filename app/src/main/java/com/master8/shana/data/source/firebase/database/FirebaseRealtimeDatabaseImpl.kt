package com.master8.shana.data.source.firebase.database

import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.master8.shana.data.source.firebase.database.dto.FirebaseMovieDto
import com.master8.shana.data.source.firebase.database.dto.FirebaseSeriesDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseRealtimeDatabaseImpl : FirebaseRealtimeDatabase {

    private val database by lazy { Firebase.database.reference }

    override val goodMovies: DatabaseReference by lazy { database.child(PATH_GOOD_MOVIES) }
    override val needToWatchMovies: DatabaseReference by lazy { database.child(PATH_NEED_TO_WATCH_MOVIES) }
    private val series: DatabaseReference by lazy { database.child(PATH_SERIES) }

    override fun putGoodMovie(movie: FirebaseMovieDto) {
        goodMovies
            .child(movie.internalId)
            .setValue(movie)
    }

    override fun putNeedToWatchMovie(movie: FirebaseMovieDto) {
        needToWatchMovies
            .child(movie.internalId)
            .setValue(movie)
    }

    override fun putSeries(series: FirebaseSeriesDto) {
        this.series
            .child(series.internalId)
            .setValue(series)
    }

    override suspend fun getAllMovies(): List<FirebaseMovieDto> = withContext(Dispatchers.IO) {
        goodMovies.getMovies() + needToWatchMovies.getMovies()
    }

    override suspend fun getAllSeriesOnce(): List<FirebaseSeriesDto> {
        return series.getSeriesOnce()
    }

    override fun getAllSeries(): Flow<List<FirebaseSeriesDto>> {
        return series.getSeries()
    }

    override suspend fun getGoodMovies(): List<FirebaseMovieDto> = withContext(Dispatchers.IO) {
        goodMovies.getMovies()
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

    private suspend fun DatabaseReference.getSeriesOnce(): List<FirebaseSeriesDto> = suspendCoroutine { continuation ->
        this.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaesError: DatabaseError) {
                continuation.resumeWithException(RuntimeException("Query was cancelled! ${databaesError.message}"))
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                continuation.resume(
                    dataSnapshot
                        .children
                        .map { it.getValue(FirebaseSeriesDto::class.java)!! }
                )

            }

        })
    }

    private fun DatabaseReference.getSeries(): Flow<List<FirebaseSeriesDto>> {
        return callbackFlow {

            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    trySend(
                        snapshot
                            .children
                            .map { it.getValue(FirebaseSeriesDto::class.java)!! }
                    )
                }

                override fun onCancelled(error: DatabaseError) {
                    cancel(error.message, error.toException())
                }

            }
            this@getSeries.addValueEventListener(listener)

            awaitClose { this@getSeries.removeEventListener(listener) }
        }
    }

    override fun removeGoodMovie(movie: FirebaseMovieDto) {
        goodMovies.child(movie.internalId).removeValue()
    }

    override fun removeNeedToWatchMovie(movie: FirebaseMovieDto) {
        needToWatchMovies.child(movie.internalId).removeValue()
    }

    private companion object {
        const val PATH_GOOD_MOVIES = "goodMovies"
        const val PATH_NEED_TO_WATCH_MOVIES = "needToWatchMovies"
        const val PATH_SERIES = "series"
    }
}