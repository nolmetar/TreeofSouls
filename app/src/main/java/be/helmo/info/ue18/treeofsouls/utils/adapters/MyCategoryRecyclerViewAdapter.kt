package be.helmo.info.ue18.treeofsouls.utils.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import be.helmo.info.ue18.treeofsouls.R
import be.helmo.info.ue18.treeofsouls.data.model.db.Category
import be.helmo.info.ue18.treeofsouls.data.model.db.Memory
import be.helmo.info.ue18.treeofsouls.ui.viewholder.CategoryViewHolder
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.fragment.CategoryViewModel


class MyCategoryRecyclerViewAdapter(
    private val categoryViewModel: CategoryViewModel,
    private val onClick: (Category) -> Unit
    ) :
    ListAdapter<Category, CategoryViewHolder>(CategoryDiffCallback,
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view, onClick, categoryViewModel)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }
}

object CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.categoryId == newItem.categoryId
    }
}