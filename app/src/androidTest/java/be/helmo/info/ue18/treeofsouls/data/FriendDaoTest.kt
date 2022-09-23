package be.helmo.info.ue18.treeofsouls.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import be.helmo.info.ue18.treeofsouls.data.local.dao.FriendDao
import be.helmo.info.ue18.treeofsouls.data.local.db.AppDatabase
import be.helmo.info.ue18.treeofsouls.data.model.db.Category
import be.helmo.info.ue18.treeofsouls.data.model.db.Friend
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
class FriendDaoTest {

    private lateinit var friendDao: FriendDao
    private lateinit var db: AppDatabase

    private var FRI_DEMO = Friend(1, "Jonathan","Kraus",Calendar.getInstance().getTime())
    private var FRI_DEMO_2 = Friend(2, "Antoine","Demany", Calendar.getInstance().getTime())
    private var FRI_DEMO_3 = Friend(3, "Jonathan","Kraus", Calendar.getInstance().getTime())


    @Before
    fun createDb(){
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).allowMainThreadQueries().build()
        friendDao = db.friendDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }


    @Test
    @Throws(Exception::class)
    fun insertAndGetFriend(): Unit = runBlocking{
        friendDao.insert(FRI_DEMO)
        val allCat = friendDao.getFriends().first()
        assertEquals(allCat[0].lastName, FRI_DEMO.lastName)
        assertEquals(allCat[0].friendId, 1)
    }

    @Test
    @Throws(Exception::class)
    fun updateFriend(): Unit = runBlocking{
        friendDao.insert(FRI_DEMO)
        assertEquals(friendDao.getFriend(1).lastName, FRI_DEMO.lastName)
        val newFri = friendDao.getFriend(1)
        newFri.lastName = "Test"
        friendDao.update(newFri)
        assertEquals(friendDao.getFriend(1).lastName, "Test")
    }

    @Test
    @Throws(Exception::class)
    fun removeFriend(): Unit = runBlocking{
        friendDao.insert(FRI_DEMO)
        friendDao.insert(FRI_DEMO_2)
        assertEquals(friendDao.getCount(), 2)
        friendDao.delete(2)
        assertEquals(friendDao.getCount(), 1)
    }

    @Test
    @Throws(Exception::class)
    fun removeAllFriend(): Unit = runBlocking{
        friendDao.insert(FRI_DEMO)
        friendDao.insert(FRI_DEMO_2)
        friendDao.insert(FRI_DEMO_3)
        assertEquals(friendDao.getCount(), 3)
        friendDao.deleteAll()
        assertEquals(friendDao.getCount(), 0)
    }

}