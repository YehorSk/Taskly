package com.yehorsk.taskly.notes.presentation.list

import com.yehorsk.taskly.core.utils.AddEditAction
import com.yehorsk.taskly.core.utils.brightColors
import com.yehorsk.taskly.notes.data.database.models.CheckItem
import com.yehorsk.taskly.notes.data.database.models.CheckList
import com.yehorsk.taskly.notes.domain.models.Note
import java.time.LocalDateTime

data class NoteListScreenUiState(
    val items: List<Note> = testList,
    val isLoading: Boolean = true,
    val currentNote: Note? = null,
    val title: String = "",
    val description: String = "",
    val color: Long = brightColors[0],
    val checkList: CheckList = CheckList(items = emptyList()),
    val action: AddEditAction = AddEditAction.ADD,
)

val testList = listOf<Note>(
        Note(
            id = 1,
            createdAt = LocalDateTime.now(),
            title = "Grocery Shopping",
            description = "Don't forget these essentials!",
            checkList = CheckList(
                items = listOf(
                    CheckItem(name = "Milk"),
                    CheckItem(name = "Eggs"),
                    CheckItem(name = "Bread"),
                    CheckItem(name = "Apples"),
                    CheckItem(name = "Cheese", isDone = true)
                )
            ) ,
            color = 0xFFFFB300
        ),
    Note(
        id = 2,
        createdAt = LocalDateTime.now(),
        title = "Lorem Ipsum",
        description = "Don't forget these essentials! 2",
        checkList = CheckList(
            items = emptyList()
        ) ,
        color = 0xFFFFB300
    )
)