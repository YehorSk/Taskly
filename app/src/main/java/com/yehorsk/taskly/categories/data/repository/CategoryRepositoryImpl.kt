package com.yehorsk.taskly.categories.data.repository

import com.yehorsk.taskly.categories.data.database.dao.CategoryDao
import com.yehorsk.taskly.categories.data.mappers.toCategory
import com.yehorsk.taskly.categories.data.mappers.toCategoryEntity
import com.yehorsk.taskly.categories.domain.models.Category
import com.yehorsk.taskly.categories.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryRepositoryImpl(
    val categoryDao: CategoryDao
) : CategoryRepository {

    override fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories()
            .map { data ->
                data.map {
                    it.toCategory()
                }
            }
    }

    override suspend fun insertCategory(category: Category) {
        return categoryDao.insertCategory(category.toCategoryEntity())
    }

    override suspend fun updateCategory(category: Category) {
        return categoryDao.updateCategory(category.toCategoryEntity())
    }

    override suspend fun deleteCategory(category: Category) {
        return categoryDao.deleteCategory(category.toCategoryEntity())
    }

}