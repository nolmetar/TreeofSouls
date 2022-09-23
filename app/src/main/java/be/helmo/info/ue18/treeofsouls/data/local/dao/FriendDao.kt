package be.helmo.info.ue18.treeofsouls.data.local.dao

import androidx.room.*
import be.helmo.info.ue18.treeofsouls.data.model.db.Friend
import be.helmo.info.ue18.treeofsouls.data.model.others.FriendWithCategories
import kotlinx.coroutines.flow.Flow

@Dao
interface FriendDao {
    @Query("SELECT * FROM friend ORDER BY friendId")
    fun getFriends(): Flow<List<Friend>>

    @Query("SELECT COUNT(*) FROM friend")
    fun getCount(): Int

    @Query("SELECT * FROM friend WHERE friendId = :friendId")
    fun getFriend(friendId: Int): Friend

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(friend: Friend)

    @Update
    fun update(friend: Friend)

    @Query("DELETE FROM friend WHERE friendId = :friendId")
    fun delete(friendId: Int)

    @Query("DELETE FROM friend")
    suspend fun deleteAll()

    @Transaction
    @Query("SELECT * FROM friend")
    fun getFriendWithCategories(): List<FriendWithCategories>
}