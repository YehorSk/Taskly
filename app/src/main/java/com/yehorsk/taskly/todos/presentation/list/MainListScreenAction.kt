package com.yehorsk.taskly.todos.presentation.list

import com.yehorsk.taskly.todos.domain.models.CategorySummary
import com.yehorsk.taskly.todos.domain.models.ToDo
import java.time.LocalDateTime

sealed interface MainListScreenAction {

    data object OnGoBackClicked: MainListScreenAction

    data class OnTitleChanged(val title: String): MainListScreenAction

    data class OnDescriptionChanged(val description: String): MainListScreenAction

    data class OnSelectedCategoryChange(val category: CategorySummary): MainListScreenAction

    data class OnDueDateChanged(val dueDate: LocalDateTime): MainListScreenAction

    data class OnAlarmChanged(val alarmOn: Boolean): MainListScreenAction

    data class OnIsDoneClicked(val todo: ToDo): MainListScreenAction

    data object OnSaveClicked: MainListScreenAction

    data object OnUpdateClicked: MainListScreenAction

    data object OnDeleteClicked: MainListScreenAction

    data object OnShowDateTimePicker: MainListScreenAction

    data object OnHideDateTimePicker: MainListScreenAction

}