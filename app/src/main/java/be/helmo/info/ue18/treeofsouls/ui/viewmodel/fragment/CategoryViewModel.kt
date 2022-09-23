package be.helmo.info.ue18.treeofsouls.ui.viewmodel.fragment

import androidx.lifecycle.*
import be.helmo.info.ue18.treeofsouls.data.local.db.CategoryDataRepository
import be.helmo.info.ue18.treeofsouls.data.model.db.Category
import be.helmo.info.ue18.treeofsouls.data.model.db.Memory
import kotlinx.coroutines.launch
import java.util.*

class CategoryViewModel(
    private val dataSource: CategoryDataRepository
    ) : ViewModel() {
    val categories: LiveData<List<Category>> = dataSource.categories.asLiveData()
    val memories: LiveData<List<Memory>> = dataSource.memories.asLiveData()

    fun getMemoryCount(category: Category): String{
        var count: Int = 0
        if(memories.value != null){
            for(memory in memories.value!!){
                if(memory.categoryIdFK == category.categoryId){
                    count += 1
                }
            }
        }
        return count.toString()
    }

}

class CategoryViewModelFactory(private val repository: CategoryDataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}