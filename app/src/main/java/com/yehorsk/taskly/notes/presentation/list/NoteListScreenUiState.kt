package com.yehorsk.taskly.notes.presentation.list

import com.yehorsk.taskly.core.utils.AddEditAction
import com.yehorsk.taskly.core.utils.UiText
import com.yehorsk.taskly.core.utils.brightColors
import com.yehorsk.taskly.notes.data.database.models.CheckItem
import com.yehorsk.taskly.notes.domain.models.Note
import com.yehorsk.taskly.notes.domain.models.SectionedNotes

data class NoteListScreenUiState(
    val items: Map<UiText, List<Note>> = emptyMap(),
    val isLoading: Boolean = true,
    val currentNote: Note? = null,
    val title: String = "",
    val description: String = "",
    val color: Long = brightColors[0],
    val checkItems:  List<CheckItem> = emptyList(),
    val action: AddEditAction = AddEditAction.ADD,
){
    val sectionedNotes = items
        .toList()
        .map { (date, notes) ->
            SectionedNotes(
                date, notes
            )
        }
}