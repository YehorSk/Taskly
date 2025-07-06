package com.yehorsk.taskly.todos.presentation.list

import com.yehorsk.taskly.todos.domain.models.CategorySummary
import com.yehorsk.taskly.todos.domain.models.ToDo
import java.time.LocalDateTime

data class MainListScreenUiState(
    val isLoading: Boolean = true,
    val selectedToDo: ToDo? = null,
    val items: List<ToDo> = emptyList(),
    val categories: List<CategorySummary> = emptyList(),
    val title: String = "",
    val description: String = "",
    val dueDate: LocalDateTime? = null,
    val showDateTimePicker: Boolean = false
)