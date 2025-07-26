package com.yehorsk.taskly.notes.presentation.list

import com.yehorsk.taskly.notes.data.database.models.CheckItem

sealed interface NoteListScreenAction {

    data object OnGoBackClicked: NoteListScreenAction

    data class OnTitleChanged(val title: String): NoteListScreenAction

    data class OnDescriptionChanged(val description: String): NoteListScreenAction

    data class OnColorChange(val color: Long): NoteListScreenAction

    data object OnCheckItemAdded: NoteListScreenAction

    data class OnCheckItemUpdated(val item: CheckItem): NoteListScreenAction

    data class OnCheckItemDeleted(val item: CheckItem): NoteListScreenAction

    data object OnSaveClicked: NoteListScreenAction

    data object OnUpdateClicked: NoteListScreenAction

}