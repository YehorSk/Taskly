package com.yehorsk.taskly.todos.domain.repository

import com.yehorsk.taskly.core.domain.DataError
import com.yehorsk.taskly.core.domain.EmptyResult
import com.yehorsk.taskly.core.domain.Result
import com.yehorsk.taskly.todos.domain.models.CategorySummary
import com.yehorsk.taskly.todos.domain.models.ToDo
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface ToDoRepository {

    fun getCategorySummaries(): Flow<List<CategorySummary>>

    fun getTodos(dates: List<LocalDate>): Flow<List<ToDo>>

    suspend fun getTodosSuspend(): List<ToDo>

    suspend fun getToDoById(id: String): Result<ToDo, DataError.Local>

    suspend fun insertTodo(toDoEntity: ToDo): EmptyResult<DataError.Local>

    suspend fun updateTodo(toDoEntity: ToDo): EmptyResult<DataError.Local>

    suspend fun deleteTodo(toDoEntity: ToDo): EmptyResult<DataError.Local>

    suspend fun onDone(todo: ToDo): EmptyResult<DataError.Local>

    suspend fun rescheduleAlarms()
}