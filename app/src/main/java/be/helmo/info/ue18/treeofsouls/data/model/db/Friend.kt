package be.helmo.info.ue18.treeofsouls.data.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import be.helmo.info.ue18.treeofsouls.utils.DateTypeConvertor
import java.util.*

@Entity(tableName = "friend")
data class Friend(
    @PrimaryKey(autoGenerate = true)
    var friendId: Int,
    var firstName: String = "",
    var lastName: String = "",
    @TypeConverters(DateTypeConvertor::class)
    var birthDate: Date
    ){
    override fun toString() = firstName + lastName
}