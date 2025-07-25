package com.yehorsk.taskly.notes.presentation.list

import com.yehorsk.taskly.todos.presentation.list.MainListScreenAction

sealed interface NoteListScreenAction {

    data object OnGoBackClicked: NoteListScreenAction

    data class OnTitleChanged(val title: String): NoteListScreenAction

    data class OnDescriptionChanged(val description: String): NoteListScreenAction

    data object OnSaveClicked: NoteListScreenAction

    data object OnUpdateClicked: NoteListScreenAction

}