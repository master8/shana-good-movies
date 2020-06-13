package com.master8.shana.domain.usecase.movies

import androidx.lifecycle.LiveData
import com.master8.shana.domain.entity.ChangedMovie
import com.master8.shana.domain.repository.MovieChangesRepository
import com.master8.shana.domain.repository.MoviesRepository

class GetChangedMovieUseCase(
    private val repository: MovieChangesRepository
) {

    operator fun invoke(): LiveData<ChangedMovie> = repository.changedMovie
}