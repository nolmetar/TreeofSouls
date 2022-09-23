package be.helmo.info.ue18.treeofsouls.ui.viewholder

import android.content.Context
import android.text.format.DateFormat
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.helmo.info.ue18.treeofsouls.R
import be.helmo.info.ue18.treeofsouls.data.model.db.Friend
import java.time.format.DateTimeFormatter


class FriendViewHolder(itemView: View, val onClick: (Friend) -> Unit) :
    RecyclerView.ViewHolder(itemView) {

    private val friendTextViewName: TextView = itemView.findViewById(R.id.friend_name)
    private val friendTextViewFirstname: TextView = itemView.findViewById(R.id.friend_first_name)
    private val friendTextViewDate: TextView = itemView.findViewById(R.id.friend_date)

    private var currentFriend: Friend? = null

    init {
        itemView.setOnClickListener {
            currentFriend?.let {
                onClick(it)
            }
        }
    }

    fun bind(friend: Friend){
        currentFriend = friend

        val date = DateFormat.format("yyyy-MM-dd",friend.birthDate)

        friendTextViewName.text = currentFriend?.lastName
        friendTextViewFirstname.text = currentFriend?.firstName
        friendTextViewDate.text = date.toString()
    }
}