package be.helmo.info.ue18.treeofsouls.ui.viewmodel.activity

import androidx.lifecycle.*
import be.helmo.info.ue18.treeofsouls.data.local.db.FriendDataRepository
import be.helmo.info.ue18.treeofsouls.data.model.db.Friend
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.fragment.FriendViewModel
import kotlinx.coroutines.launch

class SettingsViewModel (
    private val dataSource: FriendDataRepository
        ) : ViewModel(){
    val friends: LiveData<List<Friend>> = dataSource.friends.asLiveData()

    //Function pour bouton supprimer compte
    //Seulement deleteAll de friends
    fun removeAllAccount()
            = viewModelScope.launch {
        //dataSource.removeAll()
    }


}

class SettingsViewModelFactory(
    private val repositoryFriend: FriendDataRepository
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FriendViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FriendViewModel(repositoryFriend) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}