package be.helmo.info.ue18.treeofsouls.ui.viewmodel.activity

import androidx.lifecycle.*
import be.helmo.info.ue18.treeofsouls.data.local.db.CategoryDataRepository
import be.helmo.info.ue18.treeofsouls.data.local.db.FriendDataRepository
import be.helmo.info.ue18.treeofsouls.data.model.db.Category
import be.helmo.info.ue18.treeofsouls.data.model.db.Friend
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.fragment.CategoryViewModel
import kotlinx.coroutines.launch
import java.util.*

class AddCategoryViewModel(
    private val dataSource: CategoryDataRepository
    ): ViewModel(){
    val categories: LiveData<List<Category>> = dataSource.categories.asLiveData()
    val friends: LiveData<List<Friend>> = dataSource.friends.asLiveData()

    //Fonctions



    fun insertCategory(title: String, startDate: Date, isStartDateEnabled: Boolean, endDate: Date, color: Int, friends: List<Friend>)
            = viewModelScope.launch{
        val newCategory = Category(
            0,
            title,
            startDate,
            isStartDateEnabled,
            endDate,
            color
        )
        dataSource.addCategoryWithFriends(newCategory, friends)
    }

    fun getCategory(categoryID: Int) : Category?{
        return dataSource.getCategoryWithId(categoryID)
    }

    fun updateCategory(categoryID: Int, title: String, startDate: Date, isStartDateEnabled: Boolean, endDate: Date, color: Int, friends: List<Friend>)
            = viewModelScope.launch {
        val categoryUpdated = dataSource.getCategoryWithId(categoryID)

        if (categoryUpdated != null) {
            categoryUpdated.title = title
            categoryUpdated.startDate = startDate
            categoryUpdated.isStartDateEnabled = isStartDateEnabled
            categoryUpdated.endDate = endDate
            categoryUpdated.color = color
            dataSource.updateCategory(categoryUpdated)
        }
    }

    //passer de reperer grace au category courant en reperant grace a l'id comme pour update
    fun removeCategory(category: Category)
            = viewModelScope.launch {
        dataSource.removeCategory(category)
    }

    fun removeAllCategory()
            = viewModelScope.launch{
        dataSource.removeAllCategory()
    }
}

class AddCategoryViewModelFactory(private val repository: CategoryDataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddCategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddCategoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}