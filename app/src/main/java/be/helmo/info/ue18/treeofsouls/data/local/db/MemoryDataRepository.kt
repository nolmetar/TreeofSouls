package be.helmo.info.ue18.treeofsouls.data.local.db

import be.helmo.info.ue18.treeofsouls.data.local.dao.CategoryDao
import be.helmo.info.ue18.treeofsouls.data.local.dao.MemoryDao
import be.helmo.info.ue18.treeofsouls.data.model.db.Category
import be.helmo.info.ue18.treeofsouls.data.model.db.Friend
import be.helmo.info.ue18.treeofsouls.data.model.db.Memory
import be.helmo.info.ue18.treeofsouls.data.model.others.CategoryWithFriends
import kotlinx.coroutines.flow.Flow

class MemoryDataRepository (
    private var memoryDao: MemoryDao,
    private var categoryDao: CategoryDao
    ){

    var memories: Flow<List<Memory>> = memoryDao.getMemories()
    var categories: Flow<List<Category>> = categoryDao.getCategories()

    fun getMemoryCount(): Int{
        return memoryDao.getCount()
    }

    //Pas utiliser cette fonction
    fun getMemoryWithId(id: Int): Memory? {
        return memoryDao.getMemory(id)
    }

    fun getCategoryWithId(id: Int): Category? {
        return categoryDao.getCategory(id)
    }

    fun getFriends(category: Category): List<Friend>{
        var friends: List<Friend> = emptyList()
        for(catWithFrien: CategoryWithFriends in categoryDao.getCategoryWithFriends()){
            if(catWithFrien.category.categoryId == category.categoryId){
                friends = catWithFrien.friends.toList()
            }
        }
        return friends
    }

    fun addMemory(memory: Memory): Boolean{
        val beforeSize = memoryDao.getCount()
        memoryDao.insert(memory)
        val afterSize = memoryDao.getCount()

        return beforeSize != afterSize
    }

    fun updateMemory(memory: Memory): Boolean{
        val beforeUpdate = memory
        memoryDao.update(memory)

        return memory != beforeUpdate
    }

    fun removeMemory(memory: Memory): Boolean{
        val beforeSize = memoryDao.getCount()
        memoryDao.delete(memory.memoryId)
        val afterSize = memoryDao.getCount()

        return beforeSize != afterSize
    }

    suspend fun removeAllMemory(): Boolean{
        val beforeSize = memoryDao.getCount()
        memoryDao.deleteAll()
        val afterSize = memoryDao.getCount()

        return beforeSize != afterSize
    }
}