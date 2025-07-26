package com.yehorsk.taskly.notes.presentation.list

import com.yehorsk.taskly.core.utils.AddEditAction
import com.yehorsk.taskly.core.utils.brightColors
import com.yehorsk.taskly.notes.data.database.models.CheckItem
import com.yehorsk.taskly.notes.domain.models.Note

data class NoteListScreenUiState(
    val items: List<Note> = emptyList(),
    val isLoading: Boolean = true,
    val currentNote: Note? = null,
    val title: String = "",
    val description: String = "",
    val color: Long = brightColors[0],
    val checkItems:  List<CheckItem> = emptyList(),
    val action: AddEditAction = AddEditAction.ADD,
)