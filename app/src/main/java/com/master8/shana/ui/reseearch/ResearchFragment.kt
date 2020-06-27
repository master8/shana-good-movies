package com.master8.shana.ui.reseearch

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.recyclerview.widget.LinearLayoutManager
import com.master8.shana.databinding.FragmentResearchBinding

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

        LivePagedListBuilder(ResearchDataSourceFactory(), 10)
            .build()
            .observe(viewLifecycleOwner) {
                val end = SystemClock.elapsedRealtimeNanos()
                Log.e("mv8", "duration ${end - start}")

                researchAdapter.submitList(it)
            }

        return binding.root
    }
}

class ResearchDataSourceFactory : DataSource.Factory<Int, String>() {
    override fun create(): DataSource<Int, String> {
        return ResearchDataSource()
    }

}

class ResearchDataSource : PageKeyedDataSource<Int, String>() {
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, String>) {
        callback.onResult(
            IntRange(0, 30)
                .map { "text number $it" },
            null,
            null
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, String>) {

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, String>) {

    }

}