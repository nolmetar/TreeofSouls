package be.helmo.info.ue18.treeofsouls.viewmodels

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import be.helmo.info.ue18.treeofsouls.data.local.dao.FriendDao
import be.helmo.info.ue18.treeofsouls.data.local.db.AppDatabase
import be.helmo.info.ue18.treeofsouls.data.local.db.FriendDataRepository
import be.helmo.info.ue18.treeofsouls.data.model.db.Friend
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.activity.AddFriendViewModel
import kotlinx.coroutines.flow.first
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
class AddFriendVMTest {

    private lateinit var viewModel: AddFriendViewModel
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
        viewModel = AddFriendViewModel(dataSource)

    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertFriend(): Unit = runBlocking{
        assertEquals(friendDao.getCount(), 0)

        viewModel.insertFriend("Kraus","Jonathan", Calendar.getInstance().getTime())

        val allFri = friendDao.getFriends().first()
        assertEquals(allFri[0].lastName, "Kraus")
        assertEquals(allFri[0].friendId, 1)
        assertEquals(friendDao.getCount(), 1)
    }

    @Test
    @Throws(Exception::class)
    fun updateFriend(): Unit = runBlocking{
        friendDao.insert(FRI_DEMO)

        viewModel.updateFriend(1, "freeze", "Jonathan", Calendar.getInstance().getTime())

        val allFri = friendDao.getFriends().first()
        assertEquals(allFri[0].lastName, "freeze")
        assertEquals(allFri[0].friendId, 1)
        assertEquals(friendDao.getCount(), 1)
        assertEquals(friendDao.getFriend(1).lastName, "freeze")
    }

    @Test
    @Throws(Exception::class)
    fun removeFriend(): Unit = runBlocking{
        friendDao.insert(FRI_DEMO)
        friendDao.insert(FRI_DEMO_2)
        assertEquals(friendDao.getCount(), 2)

        val friend = viewModel.getFriend(2)
        viewModel.removeFriend(friend!!)


        val allFri = friendDao.getFriends().first()
        assertEquals(allFri[0].lastName, "Kraus")
        assertEquals(allFri[0].friendId, 1)
        assertEquals(friendDao.getCount(), 1)
    }

    @Test
    @Throws(Exception::class)
    fun removeAllFriend(): Unit = runBlocking{
        friendDao.insert(FRI_DEMO)
        friendDao.insert(FRI_DEMO_2)
        assertEquals(friendDao.getCount(), 2)

        viewModel.removeAllFriend()

        val allFri = friendDao.getFriends().first()
        assertEquals(friendDao.getCount(), 0)

    }

}