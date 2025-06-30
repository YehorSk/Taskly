package com.yehorsk.taskly.todos.data.mappers

import com.yehorsk.taskly.todos.data.database.models.ToDoEntity
import com.yehorsk.taskly.todos.domain.models.ToDo

fun ToDoEntity.toToDo() = ToDo(
    id = id,
    createdAt = createdAt,
    title = title,
    description = description,
    isDone = isDone,
    dueDate = dueDate
)

fun ToDo.toToDoEntity() = ToDoEntity(
    id = id,
    createdAt = createdAt,
    title = title,
    description = description,
    isDone = isDone,
    dueDate = dueDate
)