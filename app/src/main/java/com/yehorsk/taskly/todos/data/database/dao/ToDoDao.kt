package com.yehorsk.taskly.todos.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.yehorsk.taskly.todos.domain.models.CategorySummary
import com.yehorsk.taskly.todos.data.database.models.ToDoEntity
import com.yehorsk.taskly.todos.data.database.models.ToDoWithCategoryColor
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface ToDoDao {

    @Query("SELECT id, title FROM category_table")
    fun getCategorySummaries(): Flow<List<CategorySummary>>

    @Query("""
        SELECT todo_table.*, category_table.bg_color 
        FROM todo_table 
        JOIN category_table ON todo_table.category_id = category_table.id 
        WHERE date(due_date) in (:dates)
        ORDER BY time(due_date) ASC
    """)
    fun getTodosByDate(dates: List<LocalDate>): Flow<List<ToDoWithCategoryColor>>

    @Query("""
    SELECT todo_table.*, category_table.bg_color 
    FROM todo_table 
    JOIN category_table ON todo_table.category_id = category_table.id 
    ORDER BY date(due_date) ASC, time(due_date) ASC
    """)
    fun getAllTodos(): Flow<List<ToDoWithCategoryColor>>

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

    @Query("UPDATE todo_table SET is_done = 1 WHERE id = :id")
    suspend fun onDone(id: String)

}