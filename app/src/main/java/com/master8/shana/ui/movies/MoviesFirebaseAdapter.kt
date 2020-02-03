package com.master8.shana.ui.movies

import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.master8.shana.domain.entity.Movie

class MoviesFirebaseAdapter(
    options: FirebaseRecyclerOptions<Movie>
) : FirebaseRecyclerAdapter<Movie, MovieViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.createFrom(parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int, movie: Movie) {
        holder.bind(movie)
    }
}