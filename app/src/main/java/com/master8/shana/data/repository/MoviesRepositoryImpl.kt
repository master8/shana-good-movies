package com.master8.shana.data.repository

import com.master8.shana.data.source.tmdb.TMDbApiService
import com.master8.shana.data.source.tmdb.dto.MediaDto
import com.master8.shana.data.source.tmdb.mediaTypeIsMovie
import com.master8.shana.data.source.tmdb.mediaTypeIsTV
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