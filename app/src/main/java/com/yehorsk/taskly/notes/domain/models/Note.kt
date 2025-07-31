package com.yehorsk.taskly.notes.domain.models

import com.yehorsk.taskly.core.utils.UiText
import com.yehorsk.taskly.notes.data.database.models.CheckItem
import java.time.LocalDateTime

data class Note(
    val id: Int,
    val createdAt: LocalDateTime,
    val title: String,
    val description: String? = null,
    val checkItems: List<CheckItem>? = null,
    val color: Long
)


data class SectionedNotes(
    val date: UiText,
    val notes: List<Note>
)