package com.master8.shana.ui.movies.dialog

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.master8.shana.databinding.ItemMovieBinding
import com.master8.shana.databinding.ItemPosterBinding
import com.master8.shana.domain.entity.Movie
import com.master8.shana.ui.movies.MovieViewModel

open class PostersAdapter(
    private val viewModel: MovieDialogViewModel,
    private val lifecycleOwner: LifecycleOwner
) : ListAdapter<Uri, PostersAdapter.PosterViewHolder>(PosterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostersAdapter.PosterViewHolder {
        return PosterViewHolder.createFrom(parent, lifecycleOwner)
    }

    override fun onBindViewHolder(holder: PostersAdapter.PosterViewHolder, position: Int) {
        holder.bind(getItem(position), viewModel)
    }

    private class PosterDiffCallback : DiffUtil.ItemCallback<Uri>() {
        override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean {
            return oldItem == newItem
        }

    }

    class PosterViewHolder private constructor(
        private val binding: ItemPosterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(poster: Uri, viewModel: MovieDialogViewModel) {
            binding.poster = poster
            binding.viewModel = viewModel
        }

        companion object {
            fun createFrom(parent: ViewGroup, lifecycleOwner: LifecycleOwner): PosterViewHolder {
                val itemBinding = ItemPosterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                itemBinding.lifecycleOwner = lifecycleOwner
                return PosterViewHolder(itemBinding)
            }
        }
    }
}