package be.helmo.info.ue18.treeofsouls.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import be.helmo.info.ue18.treeofsouls.data.local.dao.CategoryDao
import be.helmo.info.ue18.treeofsouls.data.local.dao.FriendDao
import be.helmo.info.ue18.treeofsouls.data.local.db.AppDatabase
import be.helmo.info.ue18.treeofsouls.data.local.db.CategoryDataRepository
import be.helmo.info.ue18.treeofsouls.data.model.db.Category
import junit.framework.Assert
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
class CategoryRepoTest {
    //For data
    private lateinit var dataSource: CategoryDataRepository
    private lateinit var categoryDao: CategoryDao
    private lateinit var db: AppDatabase
    private lateinit var friendDao: FriendDao

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
        friendDao = db.friendDao()
        dataSource = CategoryDataRepository(categoryDao, friendDao)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertCategory(): Unit = runBlocking{
        dataSource.addCategory(CAT_DEMO)
        val allCat = categoryDao.getCategories().first()
        assertEquals(allCat[0].title, CAT_DEMO.title)
        assertEquals(allCat[0].categoryId, 1)
    }

    @Test
    @Throws(Exception::class)
    fun getCategoryCount(): Unit = runBlocking{
        categoryDao.insert(CAT_DEMO)
        assertEquals(dataSource.getCategoryCount(), 1)
        categoryDao.insert(CAT_DEMO_2)
        assertEquals(dataSource.getCategoryCount(), 2)

    }

    @Test
    @Throws(Exception::class)
    fun getCategory(): Unit = runBlocking{
        categoryDao.insert(CAT_DEMO)
        val cat = dataSource.getCategoryWithId(1)
        if(cat != null){
            assertEquals(cat.title, CAT_DEMO.title)
            assertEquals(cat.categoryId, 1)
        }
    }

    @Test
    @Throws(Exception::class)
    fun updateCategory(): Unit = runBlocking{
        dataSource.addCategory(CAT_DEMO)
        assertEquals(categoryDao.getCategory(1).title, CAT_DEMO.title)
        val newCat = dataSource.getCategoryWithId(1)
        if(newCat != null){
            newCat.title = "Test"
            dataSource.updateCategory(newCat)
        }
        assertEquals(categoryDao.getCategory(1).title, "Test")
    }

    @Test
    @Throws(Exception::class)
    fun removeCategory(): Unit = runBlocking{
        dataSource.addCategory(CAT_DEMO)
        dataSource.addCategory(CAT_DEMO_2)
        assertEquals(categoryDao.getCount(), 2)
        dataSource.removeCategory(CAT_DEMO)
        assertEquals(categoryDao.getCount(), 1)
    }

    @Test
    @Throws(Exception::class)
    fun removeAllCategory(): Unit = runBlocking{
        dataSource.addCategory(CAT_DEMO)
        dataSource.addCategory(CAT_DEMO_2)
        dataSource.addCategory(CAT_DEMO_3)
        assertEquals(categoryDao.getCount(), 3)
        dataSource.removeAllCategory()
        assertEquals(categoryDao.getCount(), 0)
    }
}