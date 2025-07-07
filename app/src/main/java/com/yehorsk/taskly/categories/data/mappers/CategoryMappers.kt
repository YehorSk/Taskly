package com.yehorsk.taskly.categories.data.mappers

import com.yehorsk.taskly.categories.data.database.models.CategoryEntity
import com.yehorsk.taskly.categories.data.database.models.CategoryWithCount
import com.yehorsk.taskly.categories.domain.models.Category
import com.yehorsk.taskly.categories.domain.models.CategoryMain

fun CategoryEntity.toCategory() = Category(
    id = id,
    title = title,
    createdAt = createdAt,
    bgColor = bgColor,
)

fun Category.toCategoryEntity() = CategoryEntity(
    id = id,
    title = title,
    createdAt = createdAt,
    bgColor = bgColor,
)
fun CategoryWithCount.toCategoryMain() = CategoryMain(
    category = this.category.toCategory(),
    amount = this.count
)