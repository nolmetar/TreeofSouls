package be.helmo.info.ue18.treeofsouls.data.local.db


import be.helmo.info.ue18.treeofsouls.data.local.dao.CategoryDao
import be.helmo.info.ue18.treeofsouls.data.local.dao.CategoryFriendCrossRefDao
import be.helmo.info.ue18.treeofsouls.data.local.dao.FriendDao
import be.helmo.info.ue18.treeofsouls.data.local.dao.MemoryDao
import be.helmo.info.ue18.treeofsouls.data.model.db.Friend
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
class FriendDataRepository (
    private val friendDao: FriendDao,
    private val categoryDao: CategoryDao,
    private val categoryFriendsCrossReffDao: CategoryFriendCrossRefDao,
    private val memoryDao: MemoryDao
    ){

    var friends: Flow<List<Friend>> = friendDao.getFriends()

    fun getFriendCount(): Int{
        return friendDao.getCount()
    }

    //Pas utiliser cette fonction
    fun getFriendWithId(id: Int): Friend?{
        return friendDao.getFriend(id)
    }

    fun addFriend(friend: Friend): Boolean{
        val beforeSize = friendDao.getCount()
        friendDao.insert(friend)
        val afterSize = friendDao.getCount()

        return beforeSize != afterSize
    }

    fun updateFriend(friend: Friend): Boolean{
        val beforeUpdate = friend
        friendDao.update(friend)

        return friend != beforeUpdate
    }

    fun removeFriend(friend: Friend): Boolean{
        val beforeSize = friendDao.getCount()
        friendDao.delete(friend.friendId)
        val afterSize = friendDao.getCount()

        return beforeSize != afterSize
    }

    suspend fun removeAllFriend(): Boolean{
        val beforeSize = friendDao.getCount()
        friendDao.deleteAll()
        val afterSize = friendDao.getCount()

        return beforeSize != afterSize
    }

    suspend fun removeAll(){
        friendDao.deleteAll()
        memoryDao.deleteAll()
        categoryDao.deleteAll()
        categoryFriendsCrossReffDao.deleteAll()
    }
}