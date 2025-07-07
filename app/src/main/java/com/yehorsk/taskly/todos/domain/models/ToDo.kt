package com.yehorsk.taskly.todos.domain.models

import java.time.LocalDateTime


data class ToDo(
    val id: Int = 0,
    val createdAt: LocalDateTime,
    val title: String,
    val description: String? = null,
    val isDone: Boolean,
    val dueDate: LocalDateTime? = null,
    val alarmOn: Boolean = false,
    val categoryId: Int
)

val sampleToDos = emptyList<ToDo>()