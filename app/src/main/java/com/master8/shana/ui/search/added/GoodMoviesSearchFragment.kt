package com.master8.shana.ui.search.added

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.master8.shana.app.dependency.ViewModelFactory
import com.master8.shana.databinding.FragmentAddedMoviesSearchBinding
import com.master8.shana.ui.EventObserver
import com.master8.shana.ui.movies.MovieViewModel
import com.master8.shana.ui.movies.MoviesAdapter
import com.master8.shana.ui.movies.dialog.MovieDialog
import com.master8.shana.ui.movies.dialog.MovieDialogViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GoodMoviesSearchFragment : Fragment() {

    private val viewModelsFactory by lazy { ViewModelFactory(requireContext()) }

    private val viewModel by viewModels<MoviesSearchViewModel> { viewModelsFactory }
    private val movieViewModel by viewModels<MovieViewModel> { viewModelsFactory }
    private val movieDialogViewModel by activityViewModels<MovieDialogViewModel>() { viewModelsFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentAddedMoviesSearchBinding.inflate(inflater, container, false)
            .apply {
                val adapter = MoviesAdapter(movieViewModel, viewLifecycleOwner)

                textTitle.text = "Search good movies"

                listMovies.layoutManager = LinearLayoutManager(requireContext())
                listMovies.adapter = adapter

                viewLifecycleOwner.lifecycle.coroutineScope.launch {
                    viewModel.foundMovies.collect {
                        adapter.submitList(it)
                    }
                }

                movieViewModel.openMovieDialog.observe(viewLifecycleOwner, EventObserver {
                    movieDialogViewModel.selectMovie(it)
                    MovieDialog().show(childFragmentManager, null)
                })

                val onSearch = {
                    val query: String = editSearch.text.toString()
                    viewModel.search(query)
                }

                inputLayoutSearch.setEndIconOnClickListener { onSearch() }
                editSearch.setOnEditorActionListener { _, actionId, _ ->
                    return@setOnEditorActionListener when(actionId) {
                        EditorInfo.IME_ACTION_SEARCH -> {
                            onSearch()
                            true
                        }
                        else -> false
                    }
                }
            }
            .root
    }
}