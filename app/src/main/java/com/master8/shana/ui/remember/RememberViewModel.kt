package com.master8.shana.ui.remember

import androidx.lifecycle.ViewModel
import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.usecase.movies.AddGoodMovieUseCase
import com.master8.shana.domain.usecase.movies.AddNeedToWatchMovieUseCase

class RememberViewModel(
    private val addGoodMovieUseCase: AddGoodMovieUseCase,
    private val addNeedToWatchMovieUseCase: AddNeedToWatchMovieUseCase
) : ViewModel() {

    suspend fun addGoodMovie(name: String, year: Int) {
        addGoodMovieUseCase(
            Movie(
                name = "",
                originalName = name,
                releaseYear = year
            )
        )
    }

    suspend fun addNeedToWatchMovie(name: String, year: Int) {
        addNeedToWatchMovieUseCase(
            Movie(
                name = "",
                originalName = name,
                releaseYear = year
            )
        )
    }
}