package be.helmo.info.ue18.treeofsouls.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import be.helmo.info.ue18.treeofsouls.data.model.others.CategoryFriendCrossRef
import be.helmo.info.ue18.treeofsouls.data.model.others.CategoryWithFriends

@Dao
interface CategoryFriendCrossRefDao {
    @Insert
    fun insert(join: CategoryFriendCrossRef)

    @Transaction
    @Query("SELECT * FROM Category")
    fun getCategories(): List<CategoryWithFriends>

    @Query("DELETE FROM category")
    suspend fun deleteAll()
}