package be.helmo.info.ue18.treeofsouls.utils.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import be.helmo.info.ue18.treeofsouls.R
import be.helmo.info.ue18.treeofsouls.data.model.db.Category
import be.helmo.info.ue18.treeofsouls.data.model.db.Friend
import be.helmo.info.ue18.treeofsouls.ui.viewholder.CategoryViewHolder
import be.helmo.info.ue18.treeofsouls.ui.viewholder.FriendViewHolder


class MyFriendRecyclerViewAdapter(
    private val onClick: (Friend) -> Unit) :
    ListAdapter<Friend, FriendViewHolder>(FriendDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_friend, parent, false)
        return FriendViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val friend = getItem(position)
        holder.bind(friend)
    }
}

object FriendDiffCallback : DiffUtil.ItemCallback<Friend>() {
    override fun areItemsTheSame(oldItem: Friend, newItem: Friend): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Friend, newItem: Friend): Boolean {
        return oldItem.friendId == newItem.friendId
    }
}