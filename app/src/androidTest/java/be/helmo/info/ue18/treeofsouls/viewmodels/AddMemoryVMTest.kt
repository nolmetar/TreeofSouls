package be.helmo.info.ue18.treeofsouls.viewmodels

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import be.helmo.info.ue18.treeofsouls.data.local.dao.MemoryDao
import be.helmo.info.ue18.treeofsouls.data.local.db.AppDatabase
import be.helmo.info.ue18.treeofsouls.data.local.db.MemoryDataRepository
import be.helmo.info.ue18.treeofsouls.data.model.db.Memory
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.activity.AddMemoryViewModel
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
class AddMemoryVMTest {

    private lateinit var viewModel: AddMemoryViewModel
    private lateinit var dataSource: MemoryDataRepository
    private lateinit var memoryDao: MemoryDao
    private lateinit var db: AppDatabase

    private var MEM_DEMO = Memory(1, "path1","type1","Vac2021", Calendar.getInstance().getTime(), 20.0, 10.0, 8)
    private var MEM_DEMO_2 = Memory(2, "path1","type1","Vac2020", Calendar.getInstance().getTime(), 20.0, 10.0, 8)
    private var MEM_DEMO_3 = Memory(3, "path1","type1","Vac2023", Calendar.getInstance().getTime(), 20.0, 10.0, 8)

    @Before
    fun createDb(){
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).allowMainThreadQueries().build()
        memoryDao = db.memoryDao()
        dataSource = MemoryDataRepository(memoryDao)
        viewModel = AddMemoryViewModel(dataSource)

    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertMemory(): Unit = runBlocking{
        assertEquals(memoryDao.getCount(), 0)

        viewModel.insertMemory("path1","type1","Vac2021", Calendar.getInstance().getTime(), 20.0, 10.0, 8)

        val allCat = memoryDao.getMemories().first()
        assertEquals(allCat[0].path, "path1")
        assertEquals(allCat[0].memoryId, 1)
        assertEquals(memoryDao.getCount(), 1)
    }

    @Test
    @Throws(Exception::class)
    fun updateMemory(): Unit = runBlocking{
        memoryDao.insert(MEM_DEMO)

        viewModel.updateMemory(1,"freeze","type1","Vac2021", Calendar.getInstance().getTime(), 20.0, 10.0, 8)

        val allCat = memoryDao.getMemories().first()
        assertEquals(allCat[0].path, "freeze")
        assertEquals(allCat[0].memoryId, 1)
        assertEquals(memoryDao.getCount(), 1)
        assertEquals(memoryDao.getMemory(1).path, "freeze")
    }

    @Test
    @Throws(Exception::class)
    fun removeMemory(): Unit = runBlocking{
        memoryDao.insert(MEM_DEMO)
        memoryDao.insert(MEM_DEMO_2)
        assertEquals(memoryDao.getCount(), 2)

        val category = viewModel.getMemory(2)
        viewModel.removeMemory(category!!)


        val allCat = memoryDao.getMemories().first()
        assertEquals(allCat[0].description, "Vac2021")
        assertEquals(allCat[0].memoryId, 1)
        assertEquals(memoryDao.getCount(), 1)
    }

    @Test
    @Throws(Exception::class)
    fun removeAllMemory(): Unit = runBlocking{
        memoryDao.insert(MEM_DEMO)
        memoryDao.insert(MEM_DEMO_2)
        assertEquals(memoryDao.getCount(), 2)

        viewModel.removeAllMemory()

        val allCat = memoryDao.getMemories().first()
        assertEquals(memoryDao.getCount(), 0)

    }
}