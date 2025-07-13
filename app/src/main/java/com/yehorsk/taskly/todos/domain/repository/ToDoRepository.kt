package com.yehorsk.taskly.todos.domain.repository

import com.yehorsk.taskly.todos.domain.models.CategorySummary
import com.yehorsk.taskly.todos.domain.models.ToDo
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {

    suspend fun getCategorySummaries(): List<CategorySummary>

    fun getTodos(date: String): Flow<List<ToDo>>

    suspend fun getToDoById(id: String): ToDo

    suspend fun insertTodo(toDoEntity: ToDo)

    suspend fun updateTodo(toDoEntity: ToDo)

    suspend fun deleteTodo(toDoEntity: ToDo)

}