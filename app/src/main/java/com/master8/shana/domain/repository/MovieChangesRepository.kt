package com.master8.shana.domain.repository

import androidx.lifecycle.LiveData
import com.master8.shana.domain.entity.ChangedMovie

interface MovieChangesRepository  {

    val changedMovie: LiveData<ChangedMovie>
    fun movieWasChanged(changedMovie: ChangedMovie)
}