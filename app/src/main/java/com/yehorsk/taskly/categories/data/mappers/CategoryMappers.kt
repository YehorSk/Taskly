package com.yehorsk.taskly.categories.data.mappers

import com.yehorsk.taskly.categories.data.database.models.CategoryEntity
import com.yehorsk.taskly.categories.domain.models.Category

fun CategoryEntity.toCategory() = Category(
    id = id,
    title = title,
    createdAt = createdAt,
    bgColor = bgColor,
    amountOfTasks = amountOfTasks
)

fun Category.toCategoryEntity() = CategoryEntity(
    id = id,
    title = title,
    createdAt = createdAt,
    bgColor = bgColor,
    amountOfTasks = amountOfTasks
)