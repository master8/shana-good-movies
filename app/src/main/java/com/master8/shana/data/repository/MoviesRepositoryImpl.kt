package com.master8.shana.data.repository

import androidx.lifecycle.MutableLiveData
import com.master8.shana.data.repository.converters.buildFirebaseMovieDto
import com.master8.shana.data.repository.converters.toMovie
import com.master8.shana.data.repository.converters.toSeries
import com.master8.shana.data.source.firebase.database.FirebaseRealtimeDatabase
import com.master8.shana.data.source.firebase.database.dto.FirebaseMovieDto
import com.master8.shana.data.source.tmdb.TMDbApiService
import com.master8.shana.data.source.tmdb.dto.MediaDto
import com.master8.shana.data.source.tmdb.mediaTypeIsMovie
import com.master8.shana.data.source.tmdb.mediaTypeIsTV
import com.master8.shana.domain.entity.ChangedMovie
import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepositoryImpl(
    private val tmdbApiService: TMDbApiService,
    private val firebaseRealtimeDatabase: FirebaseRealtimeDatabase
) : MoviesRepository {

    override val changedMovie = MutableLiveData<ChangedMovie>()

    override fun movieWasChanged(changedMovie: ChangedMovie) {
        this.changedMovie.value = changedMovie
    }

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

    override suspend fun searchMovies(query: String): List<Movie> {
        val medias = withContext(Dispatchers.IO) { tmdbApiService.multipleSearch(query).results }
        val movies = mutableListOf<Movie>()

        medias.forEach {
            when {
                mediaTypeIsMovie(it.mediaType) -> movies.add(it.toMovie())
                mediaTypeIsTV(it.mediaType) -> movies.addAll(getMovieForTV(it))
            }
        }

        val addedMovies = firebaseRealtimeDatabase.getAllMovies()
            .filter { it.externalId != null }

        val addedSeries = firebaseRealtimeDatabase.getAllSeries()
            .filter { it.externalId != null }

        for (i in movies.indices) {
            val addedMovie = addedMovies.find { it.externalId == movies[i].externalId }

            if (addedMovie != null) {
                movies[i] = addedMovie.toMovie()
            } else {
                movies[i].relatedSeries?.let { relatedSeries ->

                    val addedSerie = addedSeries.find { it.externalId == relatedSeries.externalId }
                    addedSerie?.let {
                        movies[i] = movies[i].copy(
                            relatedSeries = it.toSeries()
                        )
                    }
                }
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