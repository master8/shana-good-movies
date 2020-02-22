package com.master8.shana.ui.movies.added

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.Query
import com.master8.shana.app.app
import com.master8.shana.app.dependency.ViewModelFactory
import com.master8.shana.data.repository.converters.parseMovie
import com.master8.shana.data.source.firebase.database.FirebaseRealtimeDatabase
import com.master8.shana.databinding.FragmentMoviesBinding
import com.master8.shana.domain.entity.Movie
import com.master8.shana.ui.linkmovie.LinkMoviesViewModel
import com.master8.shana.ui.movies.MovieViewModel
import com.master8.shana.ui.movies.MoviesFirebaseAdapter

abstract class AddedMoviesFragment : Fragment() {

    private val viewModelFactory by lazy { ViewModelFactory(requireContext()) }

    private val viewModel by viewModels<MovieViewModel> { viewModelFactory }
    private val linkMovieViewModel by activityViewModels<LinkMoviesViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMoviesBinding.inflate(inflater, container, false)

        binding.textTitle.text = getTitle()

        val firebaseRealtimeDatabase = requireContext().app.moviesModule.firebaseRealtimeDatabase

        val options = FirebaseRecyclerOptions.Builder<Movie>()
            .setQuery(getMoviesQuery(firebaseRealtimeDatabase).orderByChild("dateAdded"), ::parseMovie)
            .setLifecycleOwner(this)
            .build()

        val adapter = MoviesFirebaseAdapter(options, viewModel, viewLifecycleOwner)

        val layoutManager = LinearLayoutManager(requireContext()).apply {
            reverseLayout = true
            stackFromEnd = true
        }
        binding.listMovies.layoutManager = layoutManager
        binding.listMovies.adapter = adapter

        viewModel.onLinkMovie.observe(viewLifecycleOwner) {
            linkMovieViewModel.searchByMovie(it)
            //TODO navigate
        }

        return binding.root
    }

    protected abstract fun getMoviesQuery(database: FirebaseRealtimeDatabase): Query
    protected abstract fun getTitle(): String
}