package com.master8.shana.domain.repository

import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.entity.Series

interface MoviesRepository {

    suspend fun addGoodMovie(movie: Movie)
    suspend fun addNeedToWatchMovie(movie: Movie)

    suspend fun moveToGoodMovies(movie: Movie)

    suspend fun updateGoodMovie(updatedMovie: Movie)
    suspend fun updateNeedToWatchMovie(updatedMovie: Movie)

    suspend fun deleteGoodMovie(movie: Movie)
    suspend fun deleteNeedToWatchMovie(movie: Movie)
    suspend fun deletePoster(movie: Movie)

    suspend fun getAllSeries(): List<Series>
}