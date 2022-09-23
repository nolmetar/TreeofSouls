package be.helmo.info.ue18.treeofsouls.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import be.helmo.info.ue18.treeofsouls.data.local.dao.CategoryDao
import be.helmo.info.ue18.treeofsouls.data.local.db.AppDatabase
import be.helmo.info.ue18.treeofsouls.data.local.db.CategoryDataRepository
import be.helmo.info.ue18.treeofsouls.data.model.db.Category
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*
import org.junit.After
import org.junit.Before
import org.junit.Test

@RunWith(AndroidJUnit4::class)
class CategoryDaoTest{
    //For data
    //private lateinit var dataSource: CategoryDataRepository
    private lateinit var categoryDao: CategoryDao
    private lateinit var db: AppDatabase

    //Dataset
    private var CAT_DEMO = Category(1, "Démonstration", Calendar.getInstance().getTime(), true, Calendar.getInstance().getTime(), 0)
    private var CAT_DEMO_2 = Category(2, "Démonstration v2", Calendar.getInstance().getTime(), true, Calendar.getInstance().getTime(), 0)
    private var CAT_DEMO_3 = Category(3, "Démonstration v3", Calendar.getInstance().getTime(), true, Calendar.getInstance().getTime(), 0)


    @Before
    fun createDb(){
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).allowMainThreadQueries().build()
        categoryDao = db.categoryDao()
        //dataSource = CategoryDataRepository(categoryDao)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetCategory(): Unit = runBlocking{
        categoryDao.insert(CAT_DEMO)
        val allCat = categoryDao.getCategories().first()
        assertEquals(allCat[0].title, CAT_DEMO.title)
        assertEquals(allCat[0].categoryId, 1)
    }

    @Test
    @Throws(Exception::class)
    fun updateCategory(): Unit = runBlocking{
        categoryDao.insert(CAT_DEMO)
        assertEquals(categoryDao.getCategory(1).title, CAT_DEMO.title)
        val newCat = categoryDao.getCategory(1)
        newCat.title = "Test"
        categoryDao.update(newCat)
        assertEquals(categoryDao.getCategory(1).title, "Test")
    }

    @Test
    @Throws(Exception::class)
    fun removeCategory(): Unit = runBlocking{
        categoryDao.insert(CAT_DEMO)
        categoryDao.insert(CAT_DEMO_2)
        assertEquals(categoryDao.getCount(), 2)
        categoryDao.delete(2)
        assertEquals(categoryDao.getCount(), 1)
    }

    @Test
    @Throws(Exception::class)
    fun removeAllCategory(): Unit = runBlocking{
        categoryDao.insert(CAT_DEMO)
        categoryDao.insert(CAT_DEMO_2)
        categoryDao.insert(CAT_DEMO_3)
        assertEquals(categoryDao.getCount(), 3)
        categoryDao.deleteAll()
        assertEquals(categoryDao.getCount(), 0)
    }
}