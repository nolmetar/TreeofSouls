package be.helmo.info.ue18.treeofsouls.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import be.helmo.info.ue18.treeofsouls.data.local.dao.FriendDao
import be.helmo.info.ue18.treeofsouls.data.local.db.AppDatabase
import be.helmo.info.ue18.treeofsouls.data.local.db.CategoryDataRepository
import be.helmo.info.ue18.treeofsouls.data.local.db.FriendDataRepository
import be.helmo.info.ue18.treeofsouls.data.model.db.Friend
import junit.framework.Assert.assertEquals
import junit.framework.TestCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.util.*

class FriendRepoTest {

    private lateinit var dataSource: FriendDataRepository
    private lateinit var friendDao: FriendDao
    private lateinit var db: AppDatabase

    private var FRI_DEMO = Friend(1, "Jonathan","Kraus", Calendar.getInstance().getTime())
    private var FRI_DEMO_2 = Friend(2, "Antoine","Demany", Calendar.getInstance().getTime())
    private var FRI_DEMO_3 = Friend(3, "Jonathan","Kraus", Calendar.getInstance().getTime())

    @Before
    fun createDb(){
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).allowMainThreadQueries().build()
        friendDao = db.friendDao()
        dataSource = FriendDataRepository(friendDao)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertFriend(): Unit = runBlocking{
        dataSource.addFriend(FRI_DEMO)
        val allCat = friendDao.getFriends().first()
        assertEquals(allCat[0].lastName, FRI_DEMO.lastName)
        assertEquals(allCat[0].friendId, 1)
    }

    @Test
    @Throws(Exception::class)
    fun getFriendCount(): Unit = runBlocking{
        friendDao.insert(FRI_DEMO)
        assertEquals(dataSource.getFriendCount(), 1)
        friendDao.insert(FRI_DEMO_2)
        assertEquals(dataSource.getFriendCount(), 2)

    }

    @Test
    @Throws(Exception::class)
    fun getFriend(): Unit = runBlocking{
        friendDao.insert(FRI_DEMO)
        val fri = dataSource.getFriendWithId(1)
        if(fri != null){
            assertEquals(fri.lastName, FRI_DEMO.lastName)
            assertEquals(fri.friendId, 1)
        }
    }

    @Test
    @Throws(Exception::class)
    fun updateFriend(): Unit = runBlocking{
        dataSource.addFriend(FRI_DEMO)
        assertEquals(friendDao.getFriend(1).lastName, FRI_DEMO.lastName)
        val newFri = dataSource.getFriendWithId(1)
        if(newFri != null){
            newFri.lastName = "Test"
            dataSource.updateFriend(newFri)
        }
        assertEquals(friendDao.getFriend(1).lastName, "Test")
    }

    @Test
    @Throws(Exception::class)
    fun removeFriend(): Unit = runBlocking{
        dataSource.addFriend(FRI_DEMO)
        dataSource.addFriend(FRI_DEMO_2)
        assertEquals(friendDao.getCount(), 2)
        dataSource.removeFriend(FRI_DEMO)
        assertEquals(friendDao.getCount(), 1)
    }

    @Test
    @Throws(Exception::class)
    fun removeAllFriend(): Unit = runBlocking{
        dataSource.addFriend(FRI_DEMO)
        dataSource.addFriend(FRI_DEMO_2)
        dataSource.addFriend(FRI_DEMO_3)
        assertEquals(friendDao.getCount(), 3)
        dataSource.removeAllFriend()
        assertEquals(friendDao.getCount(), 0)
    }
}