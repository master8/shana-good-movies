package com.master8.shana.data.repository

import com.master8.shana.data.repository.converters.buildFirebaseMovieDto
import com.master8.shana.data.source.firebase.database.FirebaseRealtimeDatabase
import com.master8.shana.data.source.firebase.database.FirebaseStorageDataSource
import com.master8.shana.data.source.firebase.database.dto.FirebaseMovieDto
import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.entity.StorageReferenceImage
import com.master8.shana.domain.entity.UriImage
import com.master8.shana.domain.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepositoryImpl(
    private val firebaseRealtimeDatabase: FirebaseRealtimeDatabase,
    private val firebaseStorageDataSource: FirebaseStorageDataSource
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

    private suspend fun putMovieUse(movie: Movie, putMovie: (FirebaseMovieDto) -> Unit) {

        val moviePoster = if (movie.poster is UriImage
            && movie.internalId != null
        ) {
            val posterReference = withContext(Dispatchers.IO) { firebaseStorageDataSource.uploadImage(
                movie.poster.reference
            ) }

            StorageReferenceImage(posterReference)
        } else {
            movie.poster
        }

        val seriesPoster = if (movie.relatedSeries?.poster is UriImage
            && movie.relatedSeries.internalId != null
        ) {
            val posterReference = withContext(Dispatchers.IO) { firebaseStorageDataSource.uploadImage(
                movie.relatedSeries.poster.reference
            ) }

            StorageReferenceImage(posterReference)
        } else {
            movie.relatedSeries?.poster
        }

        val movieDto =
            buildFirebaseMovieDto(
                movie.copy(
                    poster = moviePoster,
                    relatedSeries = movie.relatedSeries?.copy(
                        poster = seriesPoster
                    )
                )
            )

        putMovie(movieDto)
        movieDto.relatedSeries?.let { firebaseRealtimeDatabase.putSeries(it) }
    }



    override suspend fun deleteGoodMovie(movie: Movie) {
        firebaseRealtimeDatabase.removeGoodMovie(buildFirebaseMovieDto(movie))
        deletePoster(movie)
    }

    override suspend fun deleteNeedToWatchMovie(movie: Movie) {
        firebaseRealtimeDatabase.removeNeedToWatchMovie(buildFirebaseMovieDto(movie))
        deletePoster(movie)
    }

    override suspend fun deletePoster(movie: Movie) = withContext(Dispatchers.IO) {
        if (movie.poster is StorageReferenceImage) {
            movie.poster.reference.delete()
        }
    }
}