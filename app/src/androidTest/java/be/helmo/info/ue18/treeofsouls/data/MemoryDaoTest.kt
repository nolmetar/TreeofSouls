package be.helmo.info.ue18.treeofsouls.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import be.helmo.info.ue18.treeofsouls.data.local.dao.MemoryDao
import be.helmo.info.ue18.treeofsouls.data.local.db.AppDatabase
import be.helmo.info.ue18.treeofsouls.data.model.db.Memory
import junit.framework.Assert.assertEquals

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
class MemoryDaoTest {

    private lateinit var memoryDao: MemoryDao
    private lateinit var db: AppDatabase

    private var MEM_DEMO = Memory(1, "path1","type1","Vac2021",Calendar.getInstance().getTime(), 20.0, 10.0, 8)
    private var MEM_DEMO_2 = Memory(2, "path1","type1","Vac2020",Calendar.getInstance().getTime(), 20.0, 10.0, 8)
    private var MEM_DEMO_3 = Memory(3, "path1","type1","Vac2023",Calendar.getInstance().getTime(), 20.0, 10.0, 8)

    @Before
    fun createDb(){
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).allowMainThreadQueries().build()
        memoryDao = db.memoryDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetMemory(): Unit = runBlocking{
        memoryDao.insert(MEM_DEMO)
        val allCat = memoryDao.getMemories().first()
        assertEquals(allCat[0].description, MEM_DEMO.description)
        assertEquals(allCat[0].memoryId, 1)
    }

    @Test
    @Throws(Exception::class)
    fun updateMemory(): Unit = runBlocking{
        memoryDao.insert(MEM_DEMO)
        assertEquals(memoryDao.getMemory(1).description, MEM_DEMO.description)
        val newMem = memoryDao.getMemory(1)
        newMem.description = "Test"
        memoryDao.update(newMem)
        assertEquals(memoryDao.getMemory(1).description, "Test")
    }

    @Test
    @Throws(Exception::class)
    fun removeMemory(): Unit = runBlocking{
        memoryDao.insert(MEM_DEMO)
        memoryDao.insert(MEM_DEMO_2)
        assertEquals(memoryDao.getCount(), 2)
        memoryDao.delete(2)
        assertEquals(memoryDao.getCount(), 1)
    }

    @Test
    @Throws(Exception::class)
    fun removeAllMemory(): Unit = runBlocking{
        memoryDao.insert(MEM_DEMO)
        memoryDao.insert(MEM_DEMO_2)
        memoryDao.insert(MEM_DEMO_3)
        assertEquals(memoryDao.getCount(), 3)
        memoryDao.deleteAll()
        assertEquals(memoryDao.getCount(), 0)
    }
}