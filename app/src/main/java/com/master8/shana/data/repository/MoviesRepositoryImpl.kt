package com.master8.shana.data.repository

import android.net.Uri
import com.master8.shana.data.source.tmdb.TMDbApiService
import com.master8.shana.data.source.tmdb.createTMDbAbsoluteImageUrl
import com.master8.shana.data.source.tmdb.dto.MediaDto
import com.master8.shana.data.source.tmdb.mediaTypeIsMovieOrTv
import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepositoryImpl(
    private val tmdbApiService: TMDbApiService
) : MoviesRepository {

    override suspend fun addGoodMovie(movie: Movie) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun addNeedToWatchMovie(movie: Movie) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun searchMovies(query: String): List<Movie> = withContext(Dispatchers.IO) {
        tmdbApiService.multipleSearch(query).results
            .filter { mediaTypeIsMovieOrTv(it.mediaType) }
            .map { it.toMovie() }
    }

    private fun MediaDto.toMovie(): Movie = Movie(
        name,
        originalName,
        2019,
        poster?.let { Uri.parse(createTMDbAbsoluteImageUrl(poster)) },
        externalId = id
    )
}