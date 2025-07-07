package com.yehorsk.taskly.categories.domain.repository

import com.yehorsk.taskly.categories.data.database.models.CategoryWithCount
import com.yehorsk.taskly.categories.data.database.models.CategoryWithToDos
import com.yehorsk.taskly.categories.domain.models.Category
import com.yehorsk.taskly.categories.domain.models.CategoryMain
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getAllCategories(): Flow<List<Category>>

    fun getAllCategoriesWithToDos(): Flow<List<CategoryWithToDos>>

    fun getCategoriesWithToDoCount(): Flow<List<CategoryMain>>

    suspend fun insertCategory(category: Category)

    suspend fun updateCategory(category: Category)

    suspend fun deleteCategory(category: Category)

}