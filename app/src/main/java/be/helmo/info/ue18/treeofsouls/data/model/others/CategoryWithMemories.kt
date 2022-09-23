package be.helmo.info.ue18.treeofsouls.data.model.others

import androidx.room.Embedded
import androidx.room.Relation
import be.helmo.info.ue18.treeofsouls.data.model.db.Category
import be.helmo.info.ue18.treeofsouls.data.model.db.Memory

data class CategoryWithMemories(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "categoryIdFK"
    )
    val memories: List<Memory>
){
}