package com.yehorsk.taskly.todos.domain.models

import java.time.LocalDateTime


data class ToDo(
    val id: Int,
    val createdAt: LocalDateTime,
    val title: String,
    val description: String? = null,
    val isDone: Boolean,
    val dueDate: LocalDateTime? = null,
    val categoryId: Int
)

val sampleToDos = emptyList<ToDo>()