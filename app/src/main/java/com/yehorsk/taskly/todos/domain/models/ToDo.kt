package com.yehorsk.taskly.todos.domain.models

import com.yehorsk.taskly.core.utils.UiText
import java.time.LocalDateTime


data class ToDo(
    val id: Int = 0,
    val createdAt: LocalDateTime,
    val title: String,
    val description: String? = null,
    val isDone: Boolean,
    val dueDate: LocalDateTime? = null,
    val alarmOn: Boolean = false,
    val categoryId: Int,
    val bgColor: Long? = null,
)

data class SectionedToDo(
    val date: UiText,
    val todos: List<ToDo>
)

val sampleToDos = emptyList<SectionedToDo>()