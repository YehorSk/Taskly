package com.yehorsk.taskly.notes.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

data class CheckItem(
    val name: String,
    val isDone: Boolean = false
)

data class CheckList(
    val items: List<CheckItem>
)

@Entity(tableName = "note_table")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo("created_at") val createdAt: LocalDateTime,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("description") val description: String? = null,
    @ColumnInfo("check_list") val checkList: CheckList? = null,
    @ColumnInfo(name = "color") val color: Long
)
