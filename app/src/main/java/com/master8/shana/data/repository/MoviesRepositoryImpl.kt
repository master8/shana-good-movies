package com.master8.shana.data.repository

import com.master8.shana.data.repository.converters.buildFirebaseMovieDto
import com.master8.shana.data.source.firebase.database.FirebaseRealtimeDatabase
import com.master8.shana.data.source.firebase.database.dto.FirebaseMovieDto
import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.repository.MoviesRepository

class MoviesRepositoryImpl(
    private val firebaseRealtimeDatabase: FirebaseRealtimeDatabase
) : MoviesRepository {

    override suspend fun addGoodMovie(movie: Movie) {
        putMovieUse(movie, firebaseRealtimeDatabase::putGoodMovie)
    }

    override suspend fun addNeedToWatchMovie(movie: Movie) {
        putMovieUse(movie, firebaseRealtimeDatabase::putNeedToWatchMovie)
    }

    override suspend fun updateGoodMovie(updatedMovie: Movie) {
        putMovieUse(updatedMovie, firebaseRealtimeDatabase::putGoodMovie)
    }

    override suspend fun updateNeedToWatchMovie(updatedMovie: Movie) {
        putMovieUse(updatedMovie, firebaseRealtimeDatabase::putNeedToWatchMovie)
    }

    private fun putMovieUse(movie: Movie, putMovie: (FirebaseMovieDto) -> Unit) {
        val movieDto =
            buildFirebaseMovieDto(
                movie
            )
        putMovie(movieDto)
        movieDto.relatedSeries?.let { firebaseRealtimeDatabase.putSeries(it) }
    }

    override suspend fun deleteGoodMovie(movie: Movie) {
        firebaseRealtimeDatabase.removeGoodMovie(buildFirebaseMovieDto(movie))
    }

    override suspend fun deleteNeedToWatchMovie(movie: Movie) {
        firebaseRealtimeDatabase.removeNeedToWatchMovie(buildFirebaseMovieDto(movie))
    }
}