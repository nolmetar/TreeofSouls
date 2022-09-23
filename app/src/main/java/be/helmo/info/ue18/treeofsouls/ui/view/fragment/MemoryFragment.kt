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
import be.helmo.info.ue18.treeofsouls.utils.adapters.MyMemoryRecyclerViewAdapter
import be.helmo.info.ue18.treeofsouls.R
import be.helmo.info.ue18.treeofsouls.TreeOfSoulsApplication
import be.helmo.info.ue18.treeofsouls.data.model.db.Memory
import be.helmo.info.ue18.treeofsouls.ui.view.activity.AddMemoryActivity
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.fragment.MemoryViewModel
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.fragment.MemoryViewModelFactory

/**
 * A fragment representing a list of Items.
 */

const val MEMORY_ID = "memory id"

class MemoryFragment : Fragment() {

    private var columnCount = 1
    private val memoryViewModel: MemoryViewModel by viewModels {
        MemoryViewModelFactory((activity?.application as TreeOfSoulsApplication).repository_memory)
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
        val view = inflater.inflate(R.layout.fragment_memory, container, false)
        val memoryAdapter = MyMemoryRecyclerViewAdapter(memoryViewModel) { memory -> adapterOnClick(memory) }

        val recyclerView: RecyclerView = view.findViewById(R.id.memory_list)

        recyclerView.adapter = memoryAdapter
        recyclerView.setHasFixedSize(true);
        recyclerView.layoutManager = when {
            columnCount <= 1 -> LinearLayoutManager(context)
            else -> GridLayoutManager(context, columnCount)
        }

        memoryViewModel.memories.observe(viewLifecycleOwner){
            it?.let {
                memoryAdapter.submitList(it as MutableList<Memory>)
            }
        }

        val buttonfab: View = view.findViewById(R.id.memory_fab)
        buttonfab.setOnClickListener {
            fabOnClick()
        }
        return view
    }

    private fun adapterOnClick(memory: Memory) {
        val intent = Intent(getActivity(), AddMemoryActivity()::class.java)
        intent.putExtra(MEMORY_ID, memory.memoryId)
        startActivity(intent)
    }

    private fun fabOnClick() {
        val intent = Intent(getActivity(), AddMemoryActivity::class.java)
        startActivity(intent)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            MemoryFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}