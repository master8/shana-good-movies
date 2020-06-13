package com.master8.shana.domain.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import com.master8.shana.domain.entity.ChangedMovie
import com.master8.shana.domain.entity.Movie

interface MoviesRepository {

    suspend fun addGoodMovie(movie: Movie)
    suspend fun addNeedToWatchMovie(movie: Movie)

    suspend fun updateGoodMovie(updatedMovie: Movie)
    suspend fun updateNeedToWatchMovie(updatedMovie: Movie)

    suspend fun deleteGoodMovie(movie: Movie)
    suspend fun deleteNeedToWatchMovie(movie: Movie)
}