package be.helmo.info.ue18.treeofsouls.utils.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import be.helmo.info.ue18.treeofsouls.R
import be.helmo.info.ue18.treeofsouls.data.model.db.Category
import be.helmo.info.ue18.treeofsouls.data.model.db.Memory
import be.helmo.info.ue18.treeofsouls.ui.viewholder.MemoryViewHolder
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.fragment.MemoryViewModel


class MyMemoryRecyclerViewAdapter(
    private val memoryViewModel: MemoryViewModel,
    private val onClick: (Memory) -> Unit) :
    ListAdapter<Memory, MemoryViewHolder>(MemoryDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_memory_photo, parent, false)
        return MemoryViewHolder(view, onClick, memoryViewModel)
    }

    override fun onBindViewHolder(holder: MemoryViewHolder, position: Int) {
        val memory = getItem(position)
        holder.bind(memory)
    }
}

object MemoryDiffCallback : DiffUtil.ItemCallback<Memory>() {
    override fun areItemsTheSame(oldItem: Memory, newItem: Memory): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Memory, newItem: Memory): Boolean {
        return oldItem.memoryId == newItem.memoryId
    }
}