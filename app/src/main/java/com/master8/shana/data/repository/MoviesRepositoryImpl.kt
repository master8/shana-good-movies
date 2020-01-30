package com.master8.shana.data.repository

import com.master8.shana.data.source.firebase.database.FirebaseRealtimeDatabase
import com.master8.shana.data.source.firebase.database.dto.*
import com.master8.shana.data.source.tmdb.TMDbApiService
import com.master8.shana.data.source.tmdb.dto.MediaDto
import com.master8.shana.data.source.tmdb.mediaTypeIsMovie
import com.master8.shana.data.source.tmdb.mediaTypeIsTV
import com.master8.shana.domain.entity.*
import com.master8.shana.domain.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class MoviesRepositoryImpl(
    private val tmdbApiService: TMDbApiService,
    private val firebaseRealtimeDatabase: FirebaseRealtimeDatabase
) : MoviesRepository {

    override suspend fun addGoodMovie(movie: Movie) {
        addMovieWithStatusUse(movie, WatchStatus.WATCHED, firebaseRealtimeDatabase::addGoodMovie)
    }

    override suspend fun addNeedToWatchMovie(movie: Movie) {
        addMovieWithStatusUse(movie, WatchStatus.NOT_WATCHED, firebaseRealtimeDatabase::addNeedToWatchMovie)
    }

    override suspend fun searchMovies(query: String): List<Movie> {
        val medias = withContext(Dispatchers.IO) { tmdbApiService.multipleSearch(query).results }
        val movies = mutableListOf<Movie>()

        medias.forEach {
            when {
                mediaTypeIsMovie(it.mediaType) -> movies.add(it.toMovie())
                mediaTypeIsTV(it.mediaType) -> movies.addAll(getMovieForTV(it))
            }
        }

        return movies
    }

    private suspend fun getMovieForTV(mediaTv: MediaDto): List<Movie> {
        val series = mediaTv.toSeries()
        val seasons = withContext(Dispatchers.IO) { tmdbApiService.getTvDetails(mediaTv.id!!).seasons }
        return seasons.map { it.toMovie(series) }
    }

    private fun addMovieWithStatusUse(movie: Movie, watchStatus: WatchStatus, addMovie: (FirebaseMovieDto) -> Unit) {
        val seriesDto: FirebaseSeriesDto? = movie.relatedSeries?.let {
            firebaseSeriesDtoFrom(movie.relatedSeries, UUID.randomUUID())
        }

        val movieDto = firebaseMovieDtoFrom(
            movie,
            watchStatus,
            UUID.randomUUID(),
            System.currentTimeMillis(),
            seriesDto
        )

        addMovie(movieDto)
        seriesDto?.let { firebaseRealtimeDatabase.addSeries(it) }
    }
}