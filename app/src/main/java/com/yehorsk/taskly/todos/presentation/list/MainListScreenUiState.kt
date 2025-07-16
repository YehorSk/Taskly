package com.yehorsk.taskly.todos.presentation.list

import com.yehorsk.taskly.core.utils.AddEditAction
import com.yehorsk.taskly.todos.domain.models.CategorySummary
import com.yehorsk.taskly.todos.domain.models.ToDo
import java.time.LocalDate
import java.time.LocalDateTime

data class MainListScreenUiState(
    val selectedDates: List<LocalDate> = listOf(LocalDate.now()),
    val items: List<ToDo> = emptyList(),
    val isLoading: Boolean = true,
    val currentToDo: ToDo? = null,
    val selectedToDo: ToDo? = null,
    val categories: List<CategorySummary> = emptyList(),
    val selectedCategory: Int? = null,
    val title: String = "",
    val description: String = "",
    val dueDate: LocalDateTime = LocalDate.now().atTime(23, 59) ,
    val alarmOn: Boolean = false,
    val showDateTimePicker: Boolean = false,
    val action: AddEditAction = AddEditAction.ADD,
)