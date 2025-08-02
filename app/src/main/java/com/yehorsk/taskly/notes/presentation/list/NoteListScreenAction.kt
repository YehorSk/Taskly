package com.yehorsk.taskly.notes.presentation.list

import com.yehorsk.taskly.notes.data.database.models.CheckItem
import com.yehorsk.taskly.notes.domain.models.Note

sealed interface NoteListScreenAction {

    data class OnGetNoteById(val id: Int): NoteListScreenAction

    data object OnGoBackClicked: NoteListScreenAction

    data class OnTitleChanged(val title: String): NoteListScreenAction

    data class OnDescriptionChanged(val description: String): NoteListScreenAction

    data class OnColorChange(val color: Long): NoteListScreenAction

    data object OnCheckItemAdded: NoteListScreenAction

    data class OnCheckItemUpdated(val item: CheckItem): NoteListScreenAction

    data class OnCheckItemDeleted(val item: CheckItem): NoteListScreenAction

    data class OnItemClick(val note: Note): NoteListScreenAction

    data class OnNoteCheckItemClick(val note: Note, val checkItem: CheckItem): NoteListScreenAction

    data object OnSaveClicked: NoteListScreenAction

    data object OnUpdateClicked: NoteListScreenAction

    data object OnDeleteClicked: NoteListScreenAction

    data object OnAddNewNoteClicked: NoteListScreenAction

    data object OnFABClicked: NoteListScreenAction


}