package com.yehorsk.taskly.notes.data.mappers

import com.yehorsk.taskly.notes.data.database.models.NoteEntity
import com.yehorsk.taskly.notes.domain.models.Note

fun NoteEntity.toNote(): Note {
    return Note(
        id = id,
        createdAt = createdAt,
        title = title,
        description = description,
        checkItems = checkItems,
        color = color
    )
}

fun Note.toEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        createdAt = createdAt,
        title = title,
        description = description,
        checkItems = checkItems,
        color = color
    )
}