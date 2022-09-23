package be.helmo.info.ue18.treeofsouls.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import be.helmo.info.ue18.treeofsouls.data.local.dao.MemoryDao
import be.helmo.info.ue18.treeofsouls.data.local.db.AppDatabase
import be.helmo.info.ue18.treeofsouls.data.local.db.CategoryDataRepository
import be.helmo.info.ue18.treeofsouls.data.local.db.FriendDataRepository
import be.helmo.info.ue18.treeofsouls.data.local.db.MemoryDataRepository
import junit.framework.Assert.assertEquals
import be.helmo.info.ue18.treeofsouls.data.model.db.Memory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
class MemoryRepoTest {

    private lateinit var dataSource: MemoryDataRepository
    private lateinit var memoryDao: MemoryDao
    private lateinit var db: AppDatabase

    private var MEM_DEMO = Memory(1, "path1","type1","Vac2021", Calendar.getInstance().getTime(), 20.0, 10.0, 8)
    private var MEM_DEMO_2 = Memory(2, "path1","type1","Vac2020",Calendar.getInstance().getTime(), 20.0, 10.0, 8)
    private var MEM_DEMO_3 = Memory(3, "path1","type1","Vac2023",Calendar.getInstance().getTime(), 20.0, 10.0, 8)


    @Before
    fun createDb(){
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).allowMainThreadQueries().build()
        memoryDao = db.memoryDao()
        dataSource = MemoryDataRepository(memoryDao)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertMemory(): Unit = runBlocking{
        dataSource.addMemory(MEM_DEMO)
        val allCat = memoryDao.getMemories().first()
        assertEquals(allCat[0].description, MEM_DEMO.description)
        assertEquals(allCat[0].memoryId, 1)
    }

    @Test
    @Throws(Exception::class)
    fun getMemoryCount(): Unit = runBlocking{
        memoryDao.insert(MEM_DEMO)
        assertEquals(dataSource.getMemoryCount(), 1)
        memoryDao.insert(MEM_DEMO_2)
        assertEquals(dataSource.getMemoryCount(), 2)

    }

    @Test
    @Throws(Exception::class)
    fun getMemory(): Unit = runBlocking{
        memoryDao.insert(MEM_DEMO)
        val mem = dataSource.getMemoryWithId(1)
        if(mem != null){
            assertEquals(mem.description, MEM_DEMO.description)
            assertEquals(mem.memoryId, 1)
        }
    }

    @Test
    @Throws(Exception::class)
    fun updateMemory(): Unit = runBlocking{
        dataSource.addMemory(MEM_DEMO)
        assertEquals(memoryDao.getMemory(1).description, MEM_DEMO.description)
        val newMem = dataSource.getMemoryWithId(1)
        if(newMem != null){
            newMem.description = "Test"
            dataSource.updateMemory(newMem)
        }
        assertEquals(memoryDao.getMemory(1).description, "Test")
    }

    @Test
    @Throws(Exception::class)
    fun removeMemory(): Unit = runBlocking{
        dataSource.addMemory(MEM_DEMO)
        dataSource.addMemory(MEM_DEMO_2)
        assertEquals(memoryDao.getCount(), 2)
        dataSource.removeMemory(MEM_DEMO)
        assertEquals(memoryDao.getCount(), 1)
    }

    @Test
    @Throws(Exception::class)
    fun removeAllMemory(): Unit = runBlocking{
        dataSource.addMemory(MEM_DEMO)
        dataSource.addMemory(MEM_DEMO_2)
        dataSource.addMemory(MEM_DEMO_3)
        assertEquals(memoryDao.getCount(), 3)
        dataSource.removeAllMemory()
        assertEquals(memoryDao.getCount(), 0)
    }
}