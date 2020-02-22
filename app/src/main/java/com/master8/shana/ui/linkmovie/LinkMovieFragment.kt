package com.master8.shana.ui.linkmovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.master8.shana.app.dependency.ViewModelFactory
import com.master8.shana.databinding.FragmentLinkMovieBinding
import com.master8.shana.ui.movies.MovieViewModel


class LinkMovieFragment : Fragment() {

    private val viewModelFactory by lazy { ViewModelFactory(requireContext()) }

    private val movieViewModel by viewModels<MovieViewModel> { viewModelFactory }
    private val linkMovieViewModel by activityViewModels<LinkMovieViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLinkMovieBinding.inflate(inflater, container, false)
        val adapter = PagerMoviesAdapter(movieViewModel, viewLifecycleOwner)

        with(binding) {
            lifecycleOwner = viewLifecycleOwner

            pagerMovies.adapter = adapter

            linkMovieViewModel.originalMovie.observe(viewLifecycleOwner) {
                originalMovie = it
            }

            linkMovieViewModel.searchResults.observe(viewLifecycleOwner) { movies ->
                adapter.submitList(movies)
            }

            buttonSaveLink.setOnClickListener {
                linkMovieViewModel.linkWithMovie(adapter.getMovie(pagerMovies.currentItem))
                findNavController().popBackStack()
            }
        }

        return binding.root
    }
}