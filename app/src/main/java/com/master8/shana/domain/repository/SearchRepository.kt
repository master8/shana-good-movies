package com.master8.shana.domain.repository

import android.net.Uri
import com.master8.shana.domain.entity.Movie

interface SearchRepository {

    suspend fun searchMovies(query: String): List<Movie>
    suspend fun searchPosters(movie: Movie): List<Uri>
}