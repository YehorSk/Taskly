package com.yehorsk.taskly.todos.data.repository

import com.yehorsk.taskly.todos.data.database.dao.ToDoDao
import com.yehorsk.taskly.todos.data.database.models.ToDoEntity
import com.yehorsk.taskly.todos.domain.repository.ToDoRepository
import kotlinx.coroutines.flow.Flow

class ToDoRepositoryImpl(
    val toDoDao: ToDoDao
): ToDoRepository {
    override fun getTodos(): Flow<List<ToDoEntity>> {
        return toDoDao.getTodos()
    }

    override suspend fun getToDoById(id: String): ToDoEntity {
        return toDoDao.getToDoById(id)
    }

    override suspend fun insertTodo(toDoEntity: ToDoEntity) {
        return toDoDao.insertTodo(toDoEntity)
    }

    override suspend fun updateTodo(toDoEntity: ToDoEntity) {
        return toDoDao.updateTodo(toDoEntity)
    }

    override suspend fun deleteTodo(toDoEntity: ToDoEntity) {
        return toDoDao.deleteTodo(toDoEntity)
    }
}