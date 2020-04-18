package com.master8.shana.ui.movies.dialog

import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.master8.shana.app.dependency.ViewModelFactory
import com.master8.shana.databinding.DialogMovieBinding
import com.master8.shana.ui.EventObserver
import com.master8.shana.ui.ext.hide
import com.master8.shana.ui.ext.inverseVisibility
import com.master8.shana.ui.ext.show

class MovieDialog : BottomSheetDialogFragment() {

    private val viewModel by activityViewModels<MovieDialogViewModel>() { ViewModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DialogMovieBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val postersAdapter = PostersAdapter(viewModel, viewLifecycleOwner)

        viewModel.selectedMovie.observe(viewLifecycleOwner) {
            binding.movie = it
            binding.headerMarker.hide()
            TransitionManager.beginDelayedTransition(requireView().parent as ViewGroup)
            binding.groupChangePoster.hide()
        }

        viewModel.posters.observe(viewLifecycleOwner, EventObserver {
            postersAdapter.submitList(it)
            binding.headerMarker.show()
            TransitionManager.beginDelayedTransition(requireView().parent as ViewGroup)
            binding.groupChangePoster.show()
        })

        viewModel.closeDialog.observe(viewLifecycleOwner, EventObserver {
            dismiss()
        })

        binding.pagerPosters.adapter = postersAdapter

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val bottomSheetBehavior = BottomSheetBehavior.from(requireView().parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}