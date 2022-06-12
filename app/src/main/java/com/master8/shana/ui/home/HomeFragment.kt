package com.master8.shana.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.master8.shana.R
import com.master8.shana.app.app
import com.master8.shana.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.buttonGoodMovies.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_goodMoviesFragment)
        }
        binding.buttonNeedToWatchMovies.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_needToWatchMoviesFragment)
        }
        binding.buttonSearchMovie.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
        binding.buttonSearchGoodMovie.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchGoodMoviesFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            requireContext().app.moviesModule.firebaseRealtimeDatabase
                .getAllSeries()
                .collect { series ->
                    Log.e("mv8", "Series size ${series.size}")
                    series
                        .sortedByDescending { it.releaseYear }
                        .forEach {
                            Log.e("mv8", "Series ${it.name} - ${it.originalName} - ${it.externalId} - ${it.internalId}")
                        }
                }
        }
    }
}