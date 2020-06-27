package com.master8.shana.ui.reseearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.master8.shana.databinding.ItemResearchBinding

open class ResearchAdapter : PagedListAdapter<String, ResearchViewHolder>(ResearchDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResearchViewHolder {
        return ResearchViewHolder.createFrom(parent)
    }

    override fun onBindViewHolder(holder: ResearchViewHolder, position: Int) {
        holder.bind(getItem(position).orEmpty())
    }

    object ResearchDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }
}

class ResearchViewHolder private constructor(
    private val binding: ItemResearchBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(text: String) {
        binding.textItem.text = text
    }

    companion object {
        fun createFrom(parent: ViewGroup): ResearchViewHolder {
            val itemBinding = ItemResearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ResearchViewHolder(itemBinding)
        }
    }
}