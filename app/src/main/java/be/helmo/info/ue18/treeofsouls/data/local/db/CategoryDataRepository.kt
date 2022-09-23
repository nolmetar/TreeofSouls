package be.helmo.info.ue18.treeofsouls.data.local.db

import be.helmo.info.ue18.treeofsouls.data.local.dao.CategoryDao
import be.helmo.info.ue18.treeofsouls.data.local.dao.CategoryFriendCrossRefDao
import be.helmo.info.ue18.treeofsouls.data.local.dao.FriendDao
import be.helmo.info.ue18.treeofsouls.data.local.dao.MemoryDao
import be.helmo.info.ue18.treeofsouls.data.model.db.Category
import be.helmo.info.ue18.treeofsouls.data.model.db.Friend
import be.helmo.info.ue18.treeofsouls.data.model.db.Memory
import be.helmo.info.ue18.treeofsouls.data.model.others.CategoryFriendCrossRef
import be.helmo.info.ue18.treeofsouls.data.model.others.CategoryWithFriends
import kotlinx.coroutines.flow.Flow

class CategoryDataRepository (
    private var categoryDao: CategoryDao,
    private var friendDao: FriendDao,
    private var categoryFriendCrossRefDao: CategoryFriendCrossRefDao,
    private var memoryDao: MemoryDao
    ){

    var categories: Flow<List<Category>> = categoryDao.getCategories()
    var friends: Flow<List<Friend>> = friendDao.getFriends()
    var memories: Flow<List<Memory>> = memoryDao.getMemories()

    fun getCategoryCount(): Int{
        return categoryDao.getCount()
    }

    fun getCategoryWithId(id: Int): Category?{
        return categoryDao.getCategory(id)
    }

    fun addCategory(category: Category): Boolean{
        //Comparer taille categories avant et après add
        val beforeSize = categoryDao.getCount()
        categoryDao.insert(category)
        val afterSize = categoryDao.getCount()

        return beforeSize != afterSize
    }

    fun updateCategory(category: Category): Boolean{
        //Verifier changement
        val beforeUpdate = category
        categoryDao.update(category)

        return category != beforeUpdate

    }

    fun removeCategory(category: Category): Boolean{
        //Comparer taille categories avant et après add
        //revoir parce que delete prend un id
        val beforeSize = categoryDao.getCount()
        categoryDao.delete(category.categoryId)
        val afterSize = categoryDao.getCount()

        return beforeSize != afterSize
    }

    suspend fun removeAllCategory(): Boolean{
        val beforeSize = categoryDao.getCount()
        categoryDao.deleteAll()
        val afterSize = categoryDao.getCount()

        return beforeSize != afterSize
    }

    fun addCategoryWithFriends(category: Category, friends: List<Friend>): Boolean{
        //Comparer taille categories avant et après add
        val beforeSize = categoryDao.getCount()

        var idCat: Int = categoryDao.insert(category).toInt()
        for(friend in friends){
            categoryFriendCrossRefDao.insert(CategoryFriendCrossRef(idCat, friend.friendId))
        }

        val afterSize = categoryDao.getCount()

        return beforeSize != afterSize
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

    fun test(){
        categoryDao.getCategoryWithMemories().get(0).category.categoryId
        categoryDao.getCategoryWithMemories().get(0).memories.get(0).memoryId
    }
}