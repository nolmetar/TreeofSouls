package be.helmo.info.ue18.treeofsouls.ui.viewmodel.fragment

import androidx.lifecycle.*
import be.helmo.info.ue18.treeofsouls.data.local.db.FriendDataRepository
import be.helmo.info.ue18.treeofsouls.data.model.db.Friend
import kotlinx.coroutines.launch
import java.util.*


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import be.helmo.info.ue18.treeofsouls.data.model.db.Category

class FriendViewModel(
    private val dataSource: FriendDataRepository
) : ViewModel() {
    val friends: LiveData<List<Friend>> = dataSource.friends.asLiveData()

    //Fonctions à partir de dataSource


}

class FriendViewModelFactory(private val repository: FriendDataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FriendViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FriendViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

