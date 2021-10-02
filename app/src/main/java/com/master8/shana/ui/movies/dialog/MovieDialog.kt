package com.master8.shana.ui.movies.dialog

import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.master8.shana.app.dependency.ViewModelFactory
import com.master8.shana.databinding.DialogMovieBinding
import com.master8.shana.domain.entity.ReleaseStatus
import com.master8.shana.domain.entity.SaveStatus
import com.master8.shana.domain.entity.WatchStatus
import com.master8.shana.ui.EventObserver
import com.master8.shana.ui.ext.hide
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

        viewModel.selectedMovie.observe(viewLifecycleOwner) { movie ->
            binding.headerMarker.hide()
            TransitionManager.beginDelayedTransition(requireView().parent as ViewGroup)
            binding.pagerPosters.hide()
            binding.chipsNames.hide()
            binding.groupLineSeparator.hide()

            with (binding) {
                this.movie = movie

                when (movie.watchStatus) {
                    WatchStatus.WATCHED -> {
                        buttonMarkReadyToWatch.hide()
                        buttonMarkWaitingRelease.hide()
                        buttonMarkReleaseInProgress.hide()
                    }
                    WatchStatus.NOT_WATCHED,
                    WatchStatus.UNKNOWN -> {
                        when (movie.releaseStatus) {
                            ReleaseStatus.WAITING -> {
                                buttonMarkReadyToWatch.show()
                                buttonMarkWaitingRelease.hide()
                                buttonMarkReleaseInProgress.show()
                            }
                            ReleaseStatus.IN_PROGRESS -> {
                                buttonMarkReadyToWatch.show()
                                buttonMarkWaitingRelease.hide()
                                buttonMarkReleaseInProgress.hide()
                            }
                            ReleaseStatus.READY -> {
                                buttonMarkReadyToWatch.hide()
                                buttonMarkWaitingRelease.hide()
                                buttonMarkReleaseInProgress.hide()
                            }
                            ReleaseStatus.UNKNOWN -> {
                                buttonMarkReadyToWatch.show()
                                buttonMarkWaitingRelease.show()
                                buttonMarkReleaseInProgress.show()
                            }
                        }
                    }
                }

                when (movie.saveStatus) {
                    SaveStatus.SAVED -> {
                        buttonMarkNotSaved.hide()
                        buttonMarkSaving.hide()
                        buttonMarkSaved.hide()
                    }
                    SaveStatus.NOT_SAVED -> {
                        buttonMarkNotSaved.hide()
                        buttonMarkSaving.show()
                        buttonMarkSaved.show()
                    }
                    SaveStatus.UNKNOWN -> {
                        buttonMarkNotSaved.show()
                        buttonMarkSaving.show()
                        buttonMarkSaved.show()
                    }
                }
            }
        }

        viewModel.posters.observe(viewLifecycleOwner, EventObserver {
            postersAdapter.submitList(it)
            binding.headerMarker.show()
            TransitionManager.beginDelayedTransition(requireView().parent as ViewGroup)
            binding.textSeparator.text = "select poster"
            binding.groupLineSeparator.show()
            binding.chipsNames.hide()
            binding.pagerPosters.show()
        })

        viewModel.names.observe(viewLifecycleOwner, EventObserver { names ->

            binding.chipsNames.run {
                removeAllViews()
                names.forEach { name ->
                    addView(buildNameChip(name))
                }
            }


            binding.headerMarker.show()
            TransitionManager.beginDelayedTransition(requireView().parent as ViewGroup)
            binding.textSeparator.text = "select name"
            binding.groupLineSeparator.show()
            binding.pagerPosters.hide()
            binding.chipsNames.show()
        })

        viewModel.closeDialog.observe(viewLifecycleOwner, EventObserver {
            dismiss()
        })

        binding.pagerPosters.adapter = postersAdapter

        return binding.root
    }

    private fun buildNameChip(name: String) = Chip(requireContext())
        .apply {
            text = name
            setOnClickListener { viewModel.changeName(text.toString()) }
        }

    override fun onStart() {
        super.onStart()

        val bottomSheetBehavior = BottomSheetBehavior.from(requireView().parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}