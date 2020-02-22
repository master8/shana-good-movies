package com.master8.shana.ui.linkmovie

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import com.master8.shana.ui.movies.MovieViewHolder
import com.master8.shana.ui.movies.MovieViewModel
import com.master8.shana.ui.movies.MoviesAdapter

class PagerMoviesAdapter(
    viewModel: MovieViewModel,
    lifecycleOwner: LifecycleOwner
) : MoviesAdapter(
    viewModel,
    lifecycleOwner
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return super.onCreateViewHolder(parent, viewType).apply {
            itemView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }
}