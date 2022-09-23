package be.helmo.info.ue18.treeofsouls.utils

import androidx.room.TypeConverter
import java.util.*

class DateTypeConvertor {

    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return dateLong?.let { Date(it) }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

}