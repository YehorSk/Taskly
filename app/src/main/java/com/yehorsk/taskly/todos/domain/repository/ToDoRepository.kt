package com.yehorsk.taskly.todos.domain.repository

import com.yehorsk.taskly.todos.domain.models.CategorySummary
import com.yehorsk.taskly.todos.data.database.models.ToDoEntity
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {

    suspend fun getCategorySummaries(): List<CategorySummary>

    fun getTodos(): Flow<List<ToDoEntity>>

    suspend fun getToDoById(id: String): ToDoEntity

    suspend fun insertTodo(toDoEntity: ToDoEntity)

    suspend fun updateTodo(toDoEntity: ToDoEntity)

    suspend fun deleteTodo(toDoEntity: ToDoEntity)

}