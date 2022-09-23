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
import be.helmo.info.ue18.treeofsouls.utils.adapters.MyCategoryRecyclerViewAdapter
import be.helmo.info.ue18.treeofsouls.R
import be.helmo.info.ue18.treeofsouls.TreeOfSoulsApplication
import be.helmo.info.ue18.treeofsouls.data.model.db.Category
import be.helmo.info.ue18.treeofsouls.data.model.db.Memory
import be.helmo.info.ue18.treeofsouls.ui.view.activity.AddCategoryActivity
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.fragment.CategoryViewModel
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.fragment.CategoryViewModelFactory

/**
 * A fragment representing a list of Items.
 */

const val CATEGORY_ID = "category id"

class CategoryFragment : Fragment() {

    private var columnCount = 1
    private val categoryViewModel: CategoryViewModel by viewModels {
        CategoryViewModelFactory((activity?.application as TreeOfSoulsApplication).repository_category)
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
        val view = inflater.inflate(R.layout.fragment_category, container, false)
        val catgoryAdapter = MyCategoryRecyclerViewAdapter(categoryViewModel) { category -> adapterOnClick(category)}

        val recyclerView: RecyclerView = view.findViewById(R.id.category_list)

        recyclerView.adapter = catgoryAdapter
        recyclerView.setHasFixedSize(true);
        recyclerView.layoutManager = when {
            columnCount <= 1 -> LinearLayoutManager(context)
            else -> GridLayoutManager(context, columnCount)
        }

        categoryViewModel.categories.observe(viewLifecycleOwner){
            it?.let {
                catgoryAdapter.submitList(it as MutableList<Category>)
            }
        }



        val buttonfab: View = view.findViewById(R.id.category_fab)
        buttonfab.setOnClickListener {
            fabOnClick()
        }

        return view
    }

    private fun adapterOnClick(category: Category) {
        val intent = Intent(getActivity(), AddCategoryActivity()::class.java)
        intent.putExtra(CATEGORY_ID, category.categoryId)
        startActivity(intent)
    }

    private fun fabOnClick() {
        val intent = Intent(getActivity(), AddCategoryActivity::class.java)
        startActivity(intent)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            CategoryFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}