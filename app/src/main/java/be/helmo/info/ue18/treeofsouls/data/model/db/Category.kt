package be.helmo.info.ue18.treeofsouls.data.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import be.helmo.info.ue18.treeofsouls.utils.DateTypeConvertor
import java.util.*

@Entity(tableName = "category")
data class Category(
    @PrimaryKey(autoGenerate = true)
    var categoryId: Int,
    var title: String,
    @TypeConverters(DateTypeConvertor::class)
    var startDate: Date,
    var isStartDateEnabled: Boolean,
    @TypeConverters(DateTypeConvertor::class)
    var endDate: Date,
    var color: Int
    ){
    override fun toString() = title
}