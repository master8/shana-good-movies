package com.master8.shana.ui.movies.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.master8.shana.databinding.DialogMovieBinding

class MovieDialog : BottomSheetDialogFragment() {

    private val viewModel by activityViewModels<MovieDialogViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DialogMovieBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.selectedMovie.observe(viewLifecycleOwner) {
            binding.movie = it
        }

        return binding.root
    }
}