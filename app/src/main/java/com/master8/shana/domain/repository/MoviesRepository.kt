package com.master8.shana.domain.repository

import com.master8.shana.domain.entity.Movie

interface MoviesRepository {
    suspend fun addGoodMovie(movie: Movie)
    suspend fun addNeedToWatchMovie(movie: Movie)
    suspend fun searchMovies(query: String): List<Movie>
}