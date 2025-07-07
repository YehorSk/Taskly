package com.yehorsk.taskly.categories.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity("category_table")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("created_at") val createdAt: LocalDateTime,
    @ColumnInfo("bg_color") val bgColor: Long
)

data class CategoryWithCount(
    @Embedded val category: CategoryEntity,
    @ColumnInfo(name = "amount") val count: Int
)