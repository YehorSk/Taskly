package com.yehorsk.taskly.todos.data.repository

import com.yehorsk.taskly.todos.data.database.dao.ToDoDao
import com.yehorsk.taskly.todos.data.database.models.ToDoWithCategoryColor
import com.yehorsk.taskly.todos.domain.models.CategorySummary
import com.yehorsk.taskly.todos.data.mappers.toToDo
import com.yehorsk.taskly.todos.data.mappers.toToDoEntity
import com.yehorsk.taskly.todos.domain.models.ToDo
import com.yehorsk.taskly.todos.domain.repository.ToDoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ToDoRepositoryImpl(
    val toDoDao: ToDoDao
): ToDoRepository {

    override suspend fun getCategorySummaries(): List<CategorySummary> {
        return toDoDao.getCategorySummaries()
    }

    override fun getTodos(date: String): Flow<List<ToDo>> {
        return toDoDao.getTodos(date).map { data ->
            data.map {
                it.toToDo()
            }
        }
    }

    override suspend fun getToDoById(id: String): ToDo {
        return toDoDao.getToDoById(id).toToDo()
    }

    override suspend fun insertTodo(toDo: ToDo) {
        return toDoDao.insertTodo(toDo.toToDoEntity())
    }

    override suspend fun updateTodo(toDo: ToDo) {
        return toDoDao.updateTodo(toDo.toToDoEntity())
    }

    override suspend fun deleteTodo(toDo: ToDo) {
        return toDoDao.deleteTodo(toDo.toToDoEntity())
    }
}