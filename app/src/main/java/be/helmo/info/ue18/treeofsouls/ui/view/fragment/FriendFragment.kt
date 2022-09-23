package be.helmo.info.ue18.treeofsouls.ui.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import be.helmo.info.ue18.treeofsouls.utils.adapters.MyFriendRecyclerViewAdapter
import be.helmo.info.ue18.treeofsouls.R
import be.helmo.info.ue18.treeofsouls.TreeOfSoulsApplication
import be.helmo.info.ue18.treeofsouls.data.model.db.Friend
import be.helmo.info.ue18.treeofsouls.ui.view.activity.AddFriendActivity
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.fragment.FriendViewModel
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.fragment.FriendViewModelFactory

const val FRIEND_ID = "friend id"

class FriendFragment : Fragment() {

    private var columnCount = 1
    private val friendViewModel: FriendViewModel by viewModels {
        FriendViewModelFactory((activity?.application as TreeOfSoulsApplication).repository_friend)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_friend, container, false)
        val friendAdapter = MyFriendRecyclerViewAdapter() { friend -> adapterOnClick(friend) }

        val recyclerView: RecyclerView = view.findViewById(R.id.friend_list)

        recyclerView.adapter = friendAdapter
        recyclerView.setHasFixedSize(true);
        recyclerView.layoutManager = when {
            columnCount <= 1 -> LinearLayoutManager(context)
            else -> GridLayoutManager(context, columnCount)
        }


        friendViewModel.friends.observe(viewLifecycleOwner){
            it?.let {
                friendAdapter.submitList(it as MutableList<Friend>)
            }
        }

        val buttonfab: View = view.findViewById(R.id.friend_fab)
        buttonfab.setOnClickListener {
            fabOnClick()
        }
        return view
    }

    //1. Envoie l'objet
    private fun adapterOnClick(friend: Friend) {
        val intent = Intent(getActivity(), AddFriendActivity()::class.java)
        intent.putExtra(FRIEND_ID, friend.friendId)
        startActivity(intent)
    }

    private fun fabOnClick() {
        val intent = Intent(getActivity(), AddFriendActivity::class.java)
        startActivity(intent)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            FriendFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}