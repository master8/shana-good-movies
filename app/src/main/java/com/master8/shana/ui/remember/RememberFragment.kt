package com.master8.shana.ui.remember

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.master8.shana.R
import com.master8.shana.app.dependency.ViewModelFactory
import com.master8.shana.databinding.FragmentRememberBinding
import kotlinx.coroutines.launch

class RememberFragment : Fragment() {

    private val viewModelsFactory by lazy { ViewModelFactory(requireContext()) }

    private val viewModel by viewModels<RememberViewModel> { viewModelsFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentRememberBinding.inflate(inflater, container, false)
            .apply {
                buttonAddToGoodMovies.setOnClickListener {
                    lifecycleScope.launch {
                        viewModel.addGoodMovie(
                            editName.text.toString(),
                            editYear.text.toString().toInt()
                        )

                        findNavController().popBackStack()
                    }
                }

                buttonAddToNeedToWatch.setOnClickListener {
                    lifecycleScope.launch {
                        viewModel.addNeedToWatchMovie(
                            editName.text.toString(),
                            editYear.text.toString().toInt()
                        )

                        findNavController().popBackStack()
                    }
                }
            }
            .root
    }
}