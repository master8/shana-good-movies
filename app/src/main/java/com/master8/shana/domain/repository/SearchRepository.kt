package com.master8.shana.domain.repository

import com.master8.shana.domain.entity.Image
import com.master8.shana.domain.entity.Movie

interface SearchRepository {

    suspend fun searchMovies(query: String): List<Movie>
    suspend fun searchPosters(movie: Movie): List<Image>
    suspend fun searchNames(movie: Movie): List<String>
}