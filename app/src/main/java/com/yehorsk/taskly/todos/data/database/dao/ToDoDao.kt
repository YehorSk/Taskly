package com.yehorsk.taskly.todos.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.yehorsk.taskly.todos.domain.models.CategorySummary
import com.yehorsk.taskly.todos.data.database.models.ToDoEntity
import com.yehorsk.taskly.todos.data.database.models.ToDoWithCategoryColor
import com.yehorsk.taskly.todos.domain.models.ToDo
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Query("SELECT id, title FROM category_table")
    suspend fun getCategorySummaries(): List<CategorySummary>

    @Query("""
        SELECT todo_table.*, category_table.bg_color 
        FROM todo_table 
        JOIN category_table ON todo_table.category_id = category_table.id 
        WHERE date(due_date) = :date 
        ORDER BY time(due_date) ASC
    """)
    fun getTodos(date: String): Flow<List<ToDoWithCategoryColor>>

    @Query("SELECT * FROM todo_table")
    suspend fun getTodosSuspend(): List<ToDoEntity>

    @Query("SELECT * FROM todo_table WHERE id = :id")
    suspend fun getToDoById(id: String): ToDoEntity

    @Insert
    suspend fun insertTodo(toDoEntity: ToDoEntity)

    @Update
    suspend fun updateTodo(toDoEntity: ToDoEntity)

    @Delete
    suspend fun deleteTodo(toDoEntity: ToDoEntity)

}