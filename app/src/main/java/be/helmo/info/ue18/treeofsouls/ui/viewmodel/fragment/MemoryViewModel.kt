package be.helmo.info.ue18.treeofsouls.ui.viewmodel.fragment

import androidx.lifecycle.*
import be.helmo.info.ue18.treeofsouls.data.local.db.MemoryDataRepository
import be.helmo.info.ue18.treeofsouls.data.model.db.Category
import be.helmo.info.ue18.treeofsouls.data.model.db.Memory

class MemoryViewModel(
    private val dataSource: MemoryDataRepository
) : ViewModel() {
    val memories: LiveData<List<Memory>> = dataSource.memories.asLiveData()
    val categories: LiveData<List<Category>> = dataSource.categories.asLiveData()

    //Fonctions à partir de dataSource

    fun getCategoryName(memory: Memory): String{
        var nomCat = ""
        var cat: Category? = dataSource.getCategoryWithId(memory.categoryIdFK)
        if(cat != null){
            nomCat = cat.title
        }
        return nomCat
    }

    fun getFriends(memory: Memory): String{
        var listFriendsName = ""
        var cat: Category? = dataSource.getCategoryWithId(memory.categoryIdFK)
        if(cat != null){
            var listFriends = dataSource.getFriends(cat)
            for(friend in listFriends){
                listFriendsName = listFriendsName + friend.firstName + " " + friend.lastName + " "
            }
        }
        return listFriendsName
    }

}

class MemoryViewModelFactory(private val repository: MemoryDataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}