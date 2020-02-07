package com.master8.shana.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.master8.shana.databinding.ItemMovieBinding
import com.master8.shana.domain.entity.Movie

class MovieViewHolder private constructor(
    private val binding: ItemMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie, viewModel: MovieViewModel) {
        binding.movie = movie
        binding.viewModel = viewModel
    }

    companion object {
        fun createFrom(parent: ViewGroup, lifecycleOwner: LifecycleOwner): MovieViewHolder {
            val itemBinding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            itemBinding.lifecycleOwner = lifecycleOwner
            return MovieViewHolder(itemBinding)
        }
    }
}