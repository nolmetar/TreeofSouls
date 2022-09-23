package be.helmo.info.ue18.treeofsouls

import android.app.Application
import be.helmo.info.ue18.treeofsouls.data.local.db.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class TreeOfSoulsApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
    val repository_category by lazy { CategoryDataRepository(
        database.categoryDao(), database.friendDao(), database.categoryFriendsCrossReffDao(), database.memoryDao()) }
    val repository_friend by lazy { FriendDataRepository(
        database.friendDao(), database.categoryDao(), database.categoryFriendsCrossReffDao(), database.memoryDao()) }
    val repository_memory by lazy { MemoryDataRepository(database.memoryDao(), database.categoryDao()) }
}