package be.helmo.info.ue18.treeofsouls.data.model.others

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import be.helmo.info.ue18.treeofsouls.data.model.db.Category
import be.helmo.info.ue18.treeofsouls.data.model.db.Friend

data class FriendWithCategories(
    @Embedded val friend: Friend,
    @Relation(
        parentColumn = "friendId",
        entityColumn = "categoryId",
        associateBy = Junction(CategoryFriendCrossRef::class)
    )
    val categories: List<Category>
)