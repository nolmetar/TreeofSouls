package be.helmo.info.ue18.treeofsouls.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import be.helmo.info.ue18.treeofsouls.data.model.db.Category
import be.helmo.info.ue18.treeofsouls.data.model.db.Friend
import be.helmo.info.ue18.treeofsouls.data.model.others.CategoryWithFriends
import be.helmo.info.ue18.treeofsouls.data.model.others.CategoryWithMemories

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category ORDER BY title")
    fun getCategories(): Flow<List<Category>>

    @Query("SELECT COUNT(*) FROM category")
    fun getCount(): Int

    @Query("SELECT * FROM category WHERE categoryId = :categoryId")
    fun getCategory(categoryId: Int): Category

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(category: Category): Long

    @Update
    fun update(category: Category)

    @Query("DELETE FROM category WHERE categoryId = :categoryId")
    fun delete(categoryId: Int)

    @Query("DELETE FROM category")
    suspend fun deleteAll()

    @Transaction
    @Query("SELECT * FROM category")
    fun getCategoryWithMemories(): List<CategoryWithMemories>

    @Transaction
    @Query("SELECT * FROM category")
    fun getCategoryWithFriends(): List<CategoryWithFriends>
}