package com.master8.shana.domain.usecase

import androidx.lifecycle.LiveData
import com.master8.shana.domain.entity.ChangedMovie
import com.master8.shana.domain.repository.MoviesRepository

class GetChangedMovieUseCase(
    private val moviesRepository: MoviesRepository
) {

    operator fun invoke(): LiveData<ChangedMovie> = moviesRepository.changedMovie
}