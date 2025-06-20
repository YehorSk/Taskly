package com.yehorsk.taskly.categories.domain.repository

import com.yehorsk.taskly.categories.domain.models.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getAllCategories(): Flow<List<Category>>

    suspend fun upsertCategory(category: Category)

    suspend fun deleteCategory(category: Category)

}