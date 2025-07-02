package com.yehorsk.taskly.todos.presentation.list

import com.yehorsk.taskly.todos.domain.models.ToDo

data class MainListScreenUiState(
    val isLoading: Boolean = true,
    val selectedToDo: ToDo? = null,
    val items: List<ToDo> = emptyList(),
    val title: String = "",
    val description: String = "",
    val dueDate: Long? = null,
)