package be.helmo.info.ue18.treeofsouls.viewmodels

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import be.helmo.info.ue18.treeofsouls.data.local.dao.CategoryDao
import be.helmo.info.ue18.treeofsouls.data.local.dao.CategoryFriendCrossRefDao
import be.helmo.info.ue18.treeofsouls.data.local.dao.FriendDao
import be.helmo.info.ue18.treeofsouls.data.local.dao.MemoryDao
import be.helmo.info.ue18.treeofsouls.data.local.db.AppDatabase
import be.helmo.info.ue18.treeofsouls.data.local.db.CategoryDataRepository
import be.helmo.info.ue18.treeofsouls.data.model.db.Category
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.activity.AddCategoryViewModel
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
class AddCategoryVMTest {

    private lateinit var viewModel: AddCategoryViewModel
    private lateinit var dataSource: CategoryDataRepository
    private lateinit var categoryDao: CategoryDao
    private lateinit var friendDao: FriendDao
    private lateinit var memoryDao: MemoryDao
    private lateinit var categoryFriendCrossRefDao: CategoryFriendCrossRefDao
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
        friendDao = db.friendDao()
        memoryDao = db.memoryDao()
        categoryFriendCrossRefDao = db.categoryFriendsCrossReffDao()
        dataSource = CategoryDataRepository(categoryDao, friendDao,categoryFriendCrossRefDao, memoryDao)
        viewModel = AddCategoryViewModel(dataSource)

    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

   @Test
    @Throws(Exception::class)
    fun insertCategory(): Unit = runBlocking{
        assertEquals(categoryDao.getCount(), 0)

        viewModel.insertCategory("Démonstration", Calendar.getInstance().getTime(), true, Calendar.getInstance().getTime(), 0, listOf())

        val allCat = categoryDao.getCategories().first()
        assertEquals(allCat[0].title, "Démonstration")
        assertEquals(allCat[0].categoryId, 1)
        assertEquals(categoryDao.getCount(), 1)
    }

    @Test
    @Throws(Exception::class)
    fun updateCategory(): Unit = runBlocking{
        categoryDao.insert(CAT_DEMO)

        viewModel.updateCategory(1, "freeze", Calendar.getInstance().getTime(), true, Calendar.getInstance().getTime(), 0, listOf())

        val allCat = categoryDao.getCategories().first()
        assertEquals(allCat[0].title, "freeze")
        assertEquals(allCat[0].categoryId, 1)
        assertEquals(categoryDao.getCount(), 1)
        assertEquals(categoryDao.getCategory(1).title, "freeze")
    }

    @Test
    @Throws(Exception::class)
    fun removeCategory(): Unit = runBlocking{
        categoryDao.insert(CAT_DEMO)
        categoryDao.insert(CAT_DEMO_2)
        assertEquals(categoryDao.getCount(), 2)

        val category = viewModel.getCategory(2)
        viewModel.removeCategory(category!!)


        val allCat = categoryDao.getCategories().first()
        assertEquals(allCat[0].title, "Démonstration")
        assertEquals(allCat[0].categoryId, 1)
        assertEquals(categoryDao.getCount(), 1)
    }

    @Test
    @Throws(Exception::class)
    fun removeAllCategory(): Unit = runBlocking{
        categoryDao.insert(CAT_DEMO)
        categoryDao.insert(CAT_DEMO_2)
        assertEquals(categoryDao.getCount(), 2)

        viewModel.removeAllCategory()

        val allCat = categoryDao.getCategories().first()
        assertEquals(categoryDao.getCount(), 0)

    }
}