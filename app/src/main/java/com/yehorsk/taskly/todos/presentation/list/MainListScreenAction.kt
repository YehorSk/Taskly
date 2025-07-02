package com.yehorsk.taskly.todos.presentation.list

import com.yehorsk.taskly.todos.domain.models.ToDo

sealed interface MainListScreenAction {

    data object OnGoBackClicked: MainListScreenAction

    data class OnTitleChanged(val title: String): MainListScreenAction

    data class OnDescriptionChanged(val description: String): MainListScreenAction

    data class OnDueDateChanged(val dueDate: Long): MainListScreenAction

    data class OnIsDoneClicked(val todo: ToDo): MainListScreenAction

    data object OnSaveClicked: MainListScreenAction

    data object OnUpdateClicked: MainListScreenAction

    data object OnDeleteClicked: MainListScreenAction

}