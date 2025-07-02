package com.yehorsk.taskly.categories.data.database.models

import androidx.room.Embedded
import androidx.room.Relation
import com.yehorsk.taskly.todos.data.database.models.ToDoEntity

data class CategoryWithToDos(
    @Embedded val category: CategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "category_id"
    )
    val todos: List<ToDoEntity>
)
