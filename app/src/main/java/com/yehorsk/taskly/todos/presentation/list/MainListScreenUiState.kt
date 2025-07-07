package com.yehorsk.taskly.todos.presentation.list

import com.yehorsk.taskly.todos.domain.models.CategorySummary
import com.yehorsk.taskly.todos.domain.models.ToDo
import java.time.LocalDateTime

sealed interface AddEditAction{
    data object EDIT: AddEditAction
    data object ADD: AddEditAction
}

data class MainListScreenUiState(
    val action: AddEditAction = AddEditAction.ADD,
    val currentToDo: ToDo? = null,
    val isLoading: Boolean = true,
    val selectedToDo: ToDo? = null,
    val items: List<ToDo> = emptyList(),
    val categories: List<CategorySummary> = emptyList(),
    val selectedCategory: Int? = null,
    val title: String = "",
    val description: String = "",
    val dueDate: LocalDateTime? = null,
    val alarmOn: Boolean = false,
    val showDateTimePicker: Boolean = false
)