package com.yehorsk.taskly.todos.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.yehorsk.taskly.todos.data.database.models.ToDoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Query("SELECT * FROM todo_table")
    fun getTodos(): Flow<List<ToDoEntity>>

    @Query("SELECT * FROM todo_table WHERE id = :id")
    suspend fun getToDoById(id: String): ToDoEntity

    @Insert
    suspend fun insertTodo(toDoEntity: ToDoEntity)

    @Update
    suspend fun updateTodo(toDoEntity: ToDoEntity)

    @Delete
    suspend fun deleteTodo(toDoEntity: ToDoEntity)

}