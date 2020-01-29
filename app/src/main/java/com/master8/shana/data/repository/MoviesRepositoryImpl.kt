package com.master8.shana.data.repository

import android.net.Uri
import android.util.Log
import com.master8.shana.data.source.tmdb.*
import com.master8.shana.data.source.tmdb.dto.MediaDto
import com.master8.shana.data.source.tmdb.dto.SeasonDto
import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.entity.Series
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

    private fun MediaDto.toMovie() = Movie(
        name,
        originalName,
        getYearFromTMDbDate(releaseDate!!),
        createTMDbAbsoluteImageUri(poster),
        externalId = id
    )

    private fun MediaDto.toSeries() = Series(
        name,
        originalName,
        getYearFromTMDbDate(releaseDate!!),
        createTMDbAbsoluteImageUri(poster),
        externalId = id
    )

    private fun SeasonDto.toMovie(relatedSeries: Series) = Movie(
        name,
        relatedSeries.originalName,
        getYearFromTMDbDate(releaseDate),
        createTMDbAbsoluteImageUri(poster),
        externalId = id,
        relatedSeries = relatedSeries,
        episodeCount = episodeCount,
        seasonNumber = seasonNumber
    )
}