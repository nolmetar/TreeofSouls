package be.helmo.info.ue18.treeofsouls.ui.viewholder

import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import be.helmo.info.ue18.treeofsouls.R
import be.helmo.info.ue18.treeofsouls.TreeOfSoulsApplication
import be.helmo.info.ue18.treeofsouls.data.model.db.Category
import be.helmo.info.ue18.treeofsouls.data.model.db.Memory
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.activity.AddCategoryViewModel
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.activity.AddCategoryViewModelFactory
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.activity.CalendarViewModel
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.fragment.CategoryViewModel

class CategoryViewHolder(itemView: View, val onClick: (Category) -> Unit, categoryViewModel: CategoryViewModel) :
    RecyclerView.ViewHolder(itemView) {

    private val categoryTextView: TextView = itemView.findViewById(R.id.category_title)
    private val categoryTextViewNumber: TextView = itemView.findViewById(R.id.category_number)
    private val itemCategory: LinearLayout = itemView.findViewById(R.id.item_category)
    private var currentCategory: Category? = null
    private var categoryViewModel: CategoryViewModel = categoryViewModel

    init {
        itemView.setOnClickListener {
            currentCategory?.let {
                onClick(it)
            }
        }
    }

    fun bind(category: Category){
        currentCategory = category

        categoryTextView.text = currentCategory?.title
        categoryTextViewNumber.text = categoryViewModel.getMemoryCount(category)
        when(currentCategory?.color){
            1->{
                itemCategory.setBackgroundColor(Color.parseColor("#FF039BE5"))
                categoryTextView.setTextColor(Color.parseColor("#FFFFFFFF"))
                categoryTextViewNumber.setTextColor(Color.parseColor("#FFFFFFFF"))
            }
            2->{
                itemCategory.setBackgroundColor(Color.parseColor("#FF03DAC5"))
                categoryTextView.setTextColor(Color.parseColor("#FFFFFFFF"))
                categoryTextViewNumber.setTextColor(Color.parseColor("#FFFFFFFF"))
            }
            3->{
                itemCategory.setBackgroundColor(Color.parseColor("#FF6200EE"))
                categoryTextView.setTextColor(Color.parseColor("#FFFFFFFF"))
                categoryTextViewNumber.setTextColor(Color.parseColor("#FFFFFFFF"))
            }
        }

    }

}