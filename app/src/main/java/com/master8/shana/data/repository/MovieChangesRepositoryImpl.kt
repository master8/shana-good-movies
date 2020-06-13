package com.master8.shana.data.repository

import androidx.lifecycle.MutableLiveData
import com.master8.shana.domain.entity.ChangedMovie
import com.master8.shana.domain.repository.MovieChangesRepository

class MovieChangesRepositoryImpl : MovieChangesRepository {

    override val changedMovie = MutableLiveData<ChangedMovie>()

    override fun movieWasChanged(changedMovie: ChangedMovie) {
        this.changedMovie.value = changedMovie
    }
}