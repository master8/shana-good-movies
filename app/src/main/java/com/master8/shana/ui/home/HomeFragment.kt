package com.master8.shana.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.master8.shana.R
import com.master8.shana.databinding.FragmentHomeBinding

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
}