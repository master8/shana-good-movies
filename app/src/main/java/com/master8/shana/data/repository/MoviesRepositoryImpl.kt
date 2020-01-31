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
        addMovieUse(movie, firebaseRealtimeDatabase::addGoodMovie)
    }

    override suspend fun addNeedToWatchMovie(movie: Movie) {
        addMovieUse(movie, firebaseRealtimeDatabase::addNeedToWatchMovie)
    }

    private fun addMovieUse(movie: Movie, addMovie: (FirebaseMovieDto) -> Unit) {
        val movieDto = buildFirebaseMovieDto(movie)
        addMovie(movieDto)
        movieDto.relatedSeries?.let { firebaseRealtimeDatabase.addSeries(it) }
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
}