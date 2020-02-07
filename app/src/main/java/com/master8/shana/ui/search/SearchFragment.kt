package com.master8.shana.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.master8.shana.app.dependency.ViewModelFactory
import com.master8.shana.databinding.FragmentSearchBinding
import com.master8.shana.ui.movies.MovieViewModel
import com.master8.shana.ui.movies.MoviesAdapter

class SearchFragment : Fragment() {

    private val viewModelsFactory by lazy { ViewModelFactory(requireContext()) }

    private val viewModel: SearchViewModel by viewModels() { viewModelsFactory }
    private val movieViewModel: MovieViewModel by viewModels { viewModelsFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSearchBinding.inflate(inflater, container, false)
        val adapter = MoviesAdapter(movieViewModel, viewLifecycleOwner)

        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            searchViewModel = viewModel

            listMovies.layoutManager = LinearLayoutManager(requireContext())
            listMovies.adapter = adapter

            viewModel.searchResults.observe(viewLifecycleOwner) { movies ->
                adapter.submitList(movies)
            }

            inputLayoutSearch.setEndIconOnClickListener {
                val query: String = binding.editSearch.text.toString()
                viewModel.search(query)
            }
        }

        return binding.root
    }
}