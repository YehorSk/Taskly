package com.yehorsk.taskly.core.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.yehorsk.taskly.notes.data.database.models.CheckList
import kotlinx.serialization.json.Json
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Converters {

    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime?): String? {
        return value?.format(formatter)
    }

    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it, formatter) }
    }

    @TypeConverter
    fun fromLocalDate(value: LocalDate?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it) }
    }

    @TypeConverter
    fun fromCheckList(value: CheckList?): String?{
        return value?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toCheckList(value: String?): CheckList?{
        return value?.let { Gson().fromJson(it, CheckList::class.java) }
    }

}