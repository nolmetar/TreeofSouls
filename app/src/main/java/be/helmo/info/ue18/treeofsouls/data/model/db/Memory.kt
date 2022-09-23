package be.helmo.info.ue18.treeofsouls.data.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import be.helmo.info.ue18.treeofsouls.utils.DateTypeConvertor
import java.util.*

@Entity(tableName = "memory")
data class Memory(
    @PrimaryKey(autoGenerate = true)
    var memoryId: Int,
    var path: String = "",
    var type: String = "",
    var description: String = "",
    @TypeConverters(DateTypeConvertor::class)
    var dateCreated: Date,
    var latitude: Double,
    var longitude: Double,
    var categoryIdFK: Int
    ){
    override fun toString() = path
}