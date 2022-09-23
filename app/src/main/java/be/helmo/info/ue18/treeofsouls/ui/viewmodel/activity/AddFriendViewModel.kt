package be.helmo.info.ue18.treeofsouls.ui.viewmodel.activity

import androidx.lifecycle.*
import be.helmo.info.ue18.treeofsouls.data.local.db.CategoryDataRepository
import be.helmo.info.ue18.treeofsouls.data.local.db.FriendDataRepository
import be.helmo.info.ue18.treeofsouls.data.model.db.Category
import be.helmo.info.ue18.treeofsouls.data.model.db.Friend
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.fragment.FriendViewModel
import kotlinx.coroutines.launch
import java.util.*

class AddFriendViewModel(
    private val dataSource: FriendDataRepository
) : ViewModel(){
    val friends: LiveData<List<Friend>> = dataSource.friends.asLiveData()

    //Functions

    fun insertFriend(firstname: String, lastName: String, birthDate: Date)
            = viewModelScope.launch{
        val newFriend = Friend(
            0,
            lastName,
            firstname,
            birthDate,
        )
        dataSource.addFriend(newFriend)
    }

    fun getFriend(friendID: Int) : Friend?{
        return dataSource.getFriendWithId(friendID)
    }

    fun updateFriend(friendID: Int, lastname: String, firstName: String, birthDate: Date)
            = viewModelScope.launch {
        val friendUpdated = dataSource.getFriendWithId(friendID)

        if (friendUpdated != null) {
            friendUpdated.lastName = lastname
            friendUpdated.firstName = firstName
            friendUpdated.birthDate = birthDate

            dataSource.updateFriend(friendUpdated)
        }
    }

    fun removeFriend(friend: Friend)
            = viewModelScope.launch {
        dataSource.removeFriend(friend)
    }

    fun removeAllFriend()
            = viewModelScope.launch {
        dataSource.removeAllFriend()
    }
}

class AddFriendViewModelFactory(private val repository: FriendDataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddFriendViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddFriendViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}