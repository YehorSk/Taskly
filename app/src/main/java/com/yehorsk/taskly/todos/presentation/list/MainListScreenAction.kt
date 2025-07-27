package com.yehorsk.taskly.todos.presentation.list

import com.yehorsk.taskly.todos.domain.models.ToDo
import java.time.LocalDate
import java.time.LocalDateTime

sealed interface MainListScreenAction {

    data object OnGoBackClicked: MainListScreenAction

    data class OnTitleChanged(val title: String): MainListScreenAction

    data class OnDescriptionChanged(val description: String): MainListScreenAction

    data class OnSelectedCategoryChange(val category: Int): MainListScreenAction

    data class OnDueDateChanged(val dueDate: LocalDateTime): MainListScreenAction

    data class OnSelectedDateChanged(val date: LocalDate): MainListScreenAction

    data class OnAlarmChanged(val alarmOn: Boolean): MainListScreenAction

    data class OnIsDoneClicked(val todo: ToDo): MainListScreenAction

    data class OnItemClick(val todo: ToDo): MainListScreenAction

    data class OnGetToDoById(val id: Int): MainListScreenAction

    data object OnFABClicked: MainListScreenAction

    data object OnSaveClicked: MainListScreenAction

    data object OnUpdateClicked: MainListScreenAction

    data object OnDeleteClicked: MainListScreenAction

    data object OnShowDateTimePicker: MainListScreenAction

    data object OnHideDateTimePicker: MainListScreenAction

    data object OnAddNewToDoClicked: MainListScreenAction

    data object OnFullCalendarClicked: MainListScreenAction

}