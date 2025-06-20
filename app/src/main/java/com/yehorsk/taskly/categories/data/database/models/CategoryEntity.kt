package com.yehorsk.taskly.categories.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("category_table")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("created_at") val createdAt: Long,
    @ColumnInfo("bg_color") val bgColor: Long,
    @ColumnInfo("amount_of_tasks") val amountOfTasks: Int
)
