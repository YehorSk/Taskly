package com.yehorsk.taskly.core.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yehorsk.taskly.notes.data.database.models.CheckItem
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
    fun fromCheckList(value: List<CheckItem>?): String?{
        return value?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toCheckList(value: String?): List<CheckItem>? {
        val type = object : TypeToken<List<CheckItem>>() {}.type
        return value?.let { Gson().fromJson(it, type) }
    }

}