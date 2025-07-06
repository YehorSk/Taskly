package com.yehorsk.taskly.categories.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.yehorsk.taskly.categories.data.database.models.CategoryEntity
import com.yehorsk.taskly.categories.data.database.models.CategoryWithCount
import com.yehorsk.taskly.categories.data.database.models.CategoryWithToDos
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category_table")
    fun getAllCategories(): Flow<List<CategoryEntity>>
    @Transaction
//    @Query("SELECT * FROM category_table")
//    fun getAllCategoriesWithToDos(): Flow<List<CategoryWithToDos>>
//
//    @Query("""
//    SELECT c.*, COUNT(t.id) AS todo_count
//    FROM category_table c
//    LEFT JOIN todo_table t ON c.id = t.category_id
//    GROUP BY c.id
//    """)
//    fun getCategoriesWithToDoCount(): Flow<List<CategoryWithCount>>
//

    @Insert
    suspend fun insertCategory(categoryEntity: CategoryEntity)

    @Update
    suspend fun updateCategory(categoryEntity: CategoryEntity)

    @Delete
    suspend fun deleteCategory(categoryEntity: CategoryEntity)

}