package be.helmo.info.ue18.treeofsouls.ui.viewmodel.activity

import androidx.lifecycle.*
import be.helmo.info.ue18.treeofsouls.data.local.db.MemoryDataRepository
import be.helmo.info.ue18.treeofsouls.data.model.db.Category
import be.helmo.info.ue18.treeofsouls.data.model.db.Memory
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.fragment.MemoryViewModel
import kotlinx.coroutines.launch
import java.util.*

class AddMemoryViewModel (
    private val dataSource: MemoryDataRepository
    ) : ViewModel() {
    val memories: LiveData<List<Memory>> = dataSource.memories.asLiveData()
    val categories: LiveData<List<Category>> = dataSource.categories.asLiveData()

    //Functions

    fun insertMemory(path: String, type: String, description: String, dateCreated: Date, latitude: Double, longitude: Double, categoryIdFK: Int)
            = viewModelScope.launch{
        val newMemory = Memory(
            0,
            path,
            type,
            description,
            dateCreated,
            latitude,
            longitude,
            categoryIdFK,
        )
        dataSource.addMemory(newMemory)
    }

    fun getMemory(memoryID: Int) : Memory?{
        return dataSource.getMemoryWithId(memoryID)
    }

    fun updateMemory(memoryID: Int, path: String, type: String, description: String, dateCreated: Date, latitude: Double, longitude: Double, categoryIdFK: Int)
            = viewModelScope.launch {
        val memoryUpdated = dataSource.getMemoryWithId(memoryID)

        if (memoryUpdated != null) {
            memoryUpdated.path = path
            memoryUpdated.type = type
            memoryUpdated.description = description
            memoryUpdated.dateCreated = dateCreated
            memoryUpdated.latitude = latitude
            memoryUpdated.longitude = longitude
            memoryUpdated.categoryIdFK = categoryIdFK

            dataSource.updateMemory(memoryUpdated)
        }
    }

    fun removeMemory(memory: Memory)
            = viewModelScope.launch {
        dataSource.removeMemory(memory)
    }

    fun removeAllMemory()
            = viewModelScope.launch {
        dataSource.removeAllMemory()
    }
}

class AddMemoryViewModelFactory(private val repository: MemoryDataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddMemoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddMemoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}