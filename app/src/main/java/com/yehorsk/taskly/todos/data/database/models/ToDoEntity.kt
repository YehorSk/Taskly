package com.yehorsk.taskly.todos.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.yehorsk.taskly.categories.data.database.models.CategoryEntity
import java.time.LocalDateTime

@Entity(
    tableName = "todo_table",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("category_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("category_id")]
)
data class ToDoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo("created_at") val createdAt: LocalDateTime,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("description") val description: String? = null,
    @ColumnInfo("is_done") val isDone: Boolean,
    @ColumnInfo("due_date") val dueDate: LocalDateTime? = null,
    @ColumnInfo("alarm_on") val alarmOn: Boolean = false,
    @ColumnInfo("category_id") val categoryId: Int,
)

data class ToDoWithCategoryColor(
    @Embedded val todo: ToDoEntity,
    @ColumnInfo(name = "bg_color") val bgColor: Long
)