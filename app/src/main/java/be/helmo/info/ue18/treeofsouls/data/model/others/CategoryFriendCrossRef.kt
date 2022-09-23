package be.helmo.info.ue18.treeofsouls.data.model.others

import androidx.room.Entity

@Entity(primaryKeys = ["categoryId", "friendId"])
data class CategoryFriendCrossRef(
    val categoryId: Int,
    val friendId: Int
){
}