package com.master8.shana.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.master8.shana.databinding.ItemMovieBinding
import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.entity.ReleaseStatus
import com.master8.shana.domain.entity.SaveStatus
import com.master8.shana.domain.entity.WatchStatus

class MovieViewHolder private constructor(
    private val binding: ItemMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie, viewModel: MovieViewModel) {
        with (binding) {
            this.movie = movie
            this.viewModel = viewModel

            textSavedStatus.isGone = movie.saveStatus == SaveStatus.SAVED
            textSavedStatus.text = when (movie.saveStatus) {
                SaveStatus.SAVED -> "Saved"
                SaveStatus.NOT_SAVED -> "Online only"
                SaveStatus.UNKNOWN -> "Unknown"
                SaveStatus.IN_PROGRESS -> "in progress"
            }

            textReleaseStatus.isGone = movie.watchStatus == WatchStatus.WATCHED || movie.releaseStatus == ReleaseStatus.READY
            textReleaseStatus.text = when (movie.releaseStatus) {
                ReleaseStatus.WAITING -> "waiting"
                ReleaseStatus.IN_PROGRESS -> "in progress"
                ReleaseStatus.READY -> "ready"
                ReleaseStatus.UNKNOWN -> "unknown"
            }
        }
    }

    companion object {
        fun createFrom(parent: ViewGroup, lifecycleOwner: LifecycleOwner): MovieViewHolder {
            val itemBinding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            itemBinding.lifecycleOwner = lifecycleOwner
            return MovieViewHolder(itemBinding)
        }
    }
}