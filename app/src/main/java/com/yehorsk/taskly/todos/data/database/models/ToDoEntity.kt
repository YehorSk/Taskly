package com.yehorsk.taskly.todos.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class ToDoEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo("created_at") val createdAt: Long,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("description") val description: String? = null,
    @ColumnInfo("is_done") val isDone: Boolean,
    @ColumnInfo("due_date") val dueDate: Long,
)