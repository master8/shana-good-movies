package com.master8.shana.domain.usecase

import android.net.Uri
import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.repository.MoviesRepository

class SearchPostersByMovieUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movie: Movie): List<Uri> {
        return repository.searchPosters(movie)
    }
}