package be.helmo.info.ue18.treeofsouls.data.local.db

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import be.helmo.info.ue18.treeofsouls.data.local.dao.*
import be.helmo.info.ue18.treeofsouls.data.model.db.*
import be.helmo.info.ue18.treeofsouls.data.model.others.CategoryFriendCrossRef
import be.helmo.info.ue18.treeofsouls.utils.DATABASE_NAME
import be.helmo.info.ue18.treeofsouls.utils.DateTypeConvertor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@Database(
    entities = [
        Category::class,
        Friend::class,
        Memory::class,
        CategoryFriendCrossRef::class
    ], version = 1, exportSchema = false)
@TypeConverters(DateTypeConvertor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun friendDao(): FriendDao
    abstract fun memoryDao(): MemoryDao
    abstract fun categoryFriendsCrossReffDao(): CategoryFriendCrossRefDao

    companion object{
        // For Singleton instantiation
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
        private class AppDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onCreate method to populate the database.
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(
                            database.categoryDao(),
                            database.friendDao(),
                            database.memoryDao(),
                            database.categoryFriendsCrossReffDao()
                        )
                    }
                }
            }
        }
        suspend fun populateDatabase(categoryDao: CategoryDao, friendDao: FriendDao, memoryDao: MemoryDao, catFriCrossRefDao: CategoryFriendCrossRefDao) {
            categoryDao.deleteAll()
            friendDao.deleteAll()
            memoryDao.deleteAll()
            catFriCrossRefDao.deleteAll()

            /*var category: Category
            var friend: Friend
            var memory: Memory

            category = Category(0, "Catégorie 1", Calendar.getInstance().getTime(), true, Calendar.getInstance().getTime(), 0)
            categoryDao.insert(category)
            category = Category(0, "Catégorie 2", Calendar.getInstance().getTime(), true, Calendar.getInstance().getTime(), 0)
            categoryDao.insert(category)

            friend = Friend(0, "Antoine", "Demany", Calendar.getInstance().getTime())
            friendDao.insert(friend)
            friend = Friend(0, "Jonathan", "Kraus", Calendar.getInstance().getTime())
            friendDao.insert(friend)

            memory = Memory(0,"","photo","desc1",Calendar.getInstance().getTime(),5.241,50.2,1)
            memoryDao.insert(memory)
            memory = Memory(0,"","photo","desc1",Calendar.getInstance().getTime(),5.241,50.2,1)
            memoryDao.insert(memory)*/

            /*var friend: Friend
            friend = Friend(0, "Antoine", "Demany", Calendar.getInstance().getTime())
            friendDao.insert(friend)
            friend = Friend(0, "Jonathan", "Kraus", Calendar.getInstance().getTime())
            friendDao.insert(friend)*/
        }
    }
}