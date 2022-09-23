package be.helmo.info.ue18.treeofsouls.ui.viewholder

import android.graphics.Color
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.helmo.info.ue18.treeofsouls.R
import be.helmo.info.ue18.treeofsouls.data.model.db.Memory
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.fragment.MemoryViewModel

class MemoryViewHolder(
    itemView: View, val onClick: (Memory) -> Unit, memoryViewModel: MemoryViewModel) :
    RecyclerView.ViewHolder(itemView) {

    private val memoryTextViewDesc: TextView = itemView.findViewById(R.id.memory_photo_descprition)
    private val memoryTextViewFriend: TextView = itemView.findViewById(R.id.memory_photo_friend)
    private val memoryTextViewCategory: TextView = itemView.findViewById(R.id.memory_photo_category)
    private val memoryImageView: ImageView = itemView.findViewById(R.id.memory_photo_image)
    private val memoryViewModel: MemoryViewModel = memoryViewModel
    private var currentMemory: Memory? = null

    init {
        itemView.setOnClickListener {
            currentMemory?.let {
                onClick(it)
            }
        }
    }

    fun bind(memory: Memory/*, media: String*/) {
        currentMemory = memory
        val path = currentMemory?.path
        if(path != ""){
            memoryImageView.setImageURI(Uri.parse(currentMemory?.path))
        }
        memoryTextViewDesc.text = currentMemory?.description
        memoryTextViewFriend.text = memoryViewModel.getFriends(memory)
        memoryTextViewCategory.text = memoryViewModel.getCategoryName(memory)

    }
}