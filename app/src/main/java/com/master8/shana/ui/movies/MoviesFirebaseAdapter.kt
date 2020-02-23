package com.master8.shana.ui.movies

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.master8.shana.domain.entity.Movie

class MoviesFirebaseAdapter(
    options: FirebaseRecyclerOptions<Movie>,
    private val viewModel: MovieViewModel,
    private val lifecycleOwner: LifecycleOwner,
    private val dataChanged: () -> Unit
) : FirebaseRecyclerAdapter<Movie, MovieViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.createFrom(parent, lifecycleOwner)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int, movie: Movie) {
        holder.bind(movie, viewModel)
    }

    override fun onDataChanged() {
        super.onDataChanged()
        dataChanged()
    }
}