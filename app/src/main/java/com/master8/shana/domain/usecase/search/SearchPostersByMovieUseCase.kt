package com.master8.shana.domain.usecase.search

import android.net.Uri
import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.repository.SearchRepository

class SearchPostersByMovieUseCase(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(movie: Movie): List<Uri> {
        return repository.searchPosters(movie)
    }
}