package com.master8.shana.data.repository

import com.master8.shana.data.blurhash.BlurHashCreator
import com.master8.shana.data.repository.converters.buildFirebaseMovieDto
import com.master8.shana.data.repository.converters.toSeries
import com.master8.shana.data.source.firebase.database.FirebaseRealtimeDatabase
import com.master8.shana.data.source.firebase.database.FirebaseStorageDataSource
import com.master8.shana.data.source.firebase.database.dto.FirebaseMovieDto
import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.entity.Series
import com.master8.shana.domain.entity.StorageReferenceImage
import com.master8.shana.domain.entity.UriImage
import com.master8.shana.domain.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class MoviesRepositoryImpl(
    private val firebaseRealtimeDatabase: FirebaseRealtimeDatabase,
    private val firebaseStorageDataSource: FirebaseStorageDataSource,
    private val blurHashCreator: BlurHashCreator
) : MoviesRepository {

    override suspend fun addGoodMovie(movie: Movie) {
        putMovieUse(movie, firebaseRealtimeDatabase::putGoodMovie)
    }

    override suspend fun addNeedToWatchMovie(movie: Movie) {
        putMovieUse(movie, firebaseRealtimeDatabase::putNeedToWatchMovie)
    }

    override suspend fun moveToGoodMovies(movie: Movie) {
        firebaseRealtimeDatabase.removeNeedToWatchMovie(buildFirebaseMovieDto(movie))
        addGoodMovie(movie)
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
            val posterReference = withContext(Dispatchers.IO) {
                firebaseStorageDataSource.uploadImage(
                    movie.poster.reference
                )
            }

            StorageReferenceImage(
                reference = posterReference,
                blurHash = blurHashCreator.create(movie.poster.reference)
            )
        } else {
            movie.poster
        }

        val seriesPoster = if (movie.relatedSeries?.poster is UriImage
            && movie.relatedSeries.internalId == null
        ) {
            val posterReference = withContext(Dispatchers.IO) {
                firebaseStorageDataSource.uploadImage(
                    movie.relatedSeries.poster.reference
                )
            }

            StorageReferenceImage(
                reference = posterReference,
                blurHash = blurHashCreator.create(movie.relatedSeries.poster.reference)
            )
        } else {
            movie.relatedSeries?.poster
        }

        var internalId: UUID? = null

        val movieDto =
            buildFirebaseMovieDto(
                movie.copy(
                    poster = moviePoster,
                    relatedSeries = movie.relatedSeries?.copy(
                        poster = seriesPoster,
                        internalId = movie.relatedSeries.internalId ?: kotlin.run {
                            internalId = generateInternalId()
                            internalId
                        }
                    )
                )
            )

        putMovie(movieDto)

        movieDto.relatedSeries?.let { if (internalId != null) firebaseRealtimeDatabase.putSeries(it) }
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

    override suspend fun getAllSeries(): List<Series> =
        firebaseRealtimeDatabase.getAllSeries().map { it.toSeries() }

    private fun generateInternalId(): UUID = UUID.randomUUID()
}