package com.master8.shana.ui.reseearch

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.master8.shana.databinding.FragmentResearchBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ResearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentResearchBinding.inflate(inflater, container, false)
        val researchAdapter = ResearchAdapter()

        binding.listMovies.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = researchAdapter
        }

        val start = SystemClock.elapsedRealtimeNanos()

        lifecycleScope.launch {
            Pager(PagingConfig(10)) {
                ResearchDataSource()
            }.flow
                .collectLatest {
                    val end = SystemClock.elapsedRealtimeNanos()
                    Log.e("mv8", "duration ${end - start}")

                    researchAdapter.submitData(it)
                }
        }

        return binding.root
    }
}

class ResearchDataSource : PagingSource<Int, String>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, String> {
        return LoadResult.Page(
            IntRange(0, 30)
                .map { "text number $it" },
            null,
            null
        )
    }

}