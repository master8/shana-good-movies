package com.master8.shana.ui.movies.added

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.master8.shana.app.app
import com.master8.shana.app.dependency.ViewModelFactory
import com.master8.shana.data.repository.converters.parseMovie
import com.master8.shana.databinding.FragmentMoviesBinding
import com.master8.shana.domain.entity.Movie
import com.master8.shana.ui.movies.MovieViewModel
import com.master8.shana.ui.movies.MoviesFirebaseAdapter

class NeedToWatchMoviesFragment : Fragment() {

    val viewModel by viewModels<MovieViewModel> { ViewModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMoviesBinding.inflate(inflater, container, false)

        val firebaseRealtimeDatabase = requireContext().app.moviesModule.firebaseRealtimeDatabase

        val options = FirebaseRecyclerOptions.Builder<Movie>()
            .setQuery(firebaseRealtimeDatabase.needToWatchMovies.orderByChild("dateAdded"), ::parseMovie)
            .setLifecycleOwner(this)
            .build()

        val adapter = MoviesFirebaseAdapter(options, viewModel, viewLifecycleOwner)

        val layoutManager = LinearLayoutManager(requireContext()).apply {
            reverseLayout = true
            stackFromEnd = true
        }
        binding.listMovies.layoutManager = layoutManager
        binding.listMovies.adapter = adapter

        return binding.root
    }
}