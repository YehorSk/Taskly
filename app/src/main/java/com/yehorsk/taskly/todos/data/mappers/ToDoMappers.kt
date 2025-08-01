package com.yehorsk.taskly.todos.data.mappers

import com.yehorsk.taskly.todos.data.database.models.ToDoEntity
import com.yehorsk.taskly.todos.data.database.models.ToDoWithCategoryColor
import com.yehorsk.taskly.todos.domain.models.ToDo

fun ToDoEntity.toToDo() = ToDo(
    id = id,
    createdAt = createdAt,
    title = title,
    description = description,
    isDone = isDone,
    dueDate = dueDate,
    alarmOn = alarmOn,
    categoryId = categoryId
)

fun ToDo.toToDoEntity() = ToDoEntity(
    id = id,
    createdAt = createdAt,
    title = title,
    description = description,
    isDone = isDone,
    dueDate = dueDate,
    alarmOn = alarmOn,
    categoryId = categoryId,
)

fun ToDoWithCategoryColor.toToDo() = ToDo(
    id = todo.id,
    createdAt = todo.createdAt,
    title = todo.title,
    description = todo.description,
    isDone = todo.isDone,
    dueDate = todo.dueDate,
    alarmOn = todo.alarmOn,
    categoryId = todo.categoryId,
    bgColor = bgColor
)