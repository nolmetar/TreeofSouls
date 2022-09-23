package be.helmo.info.ue18.treeofsouls.data.local.dao

import androidx.room.*
import be.helmo.info.ue18.treeofsouls.data.model.db.Memory
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoryDao {
    @Query("SELECT * FROM memory ORDER BY memoryId")
    fun getMemories(): Flow<List<Memory>>

    @Query("SELECT COUNT(*) FROM memory")
    fun getCount(): Int

    @Query("SELECT * FROM memory WHERE memoryId = :memoryId")
    fun getMemory(memoryId: Int): Memory

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(memory: Memory)

    @Update
    fun update(memory: Memory)

    @Query("DELETE FROM memory WHERE memoryId = :memoryId")
    fun delete(memoryId: Int)

    @Query("DELETE FROM memory")
    suspend fun deleteAll()
}