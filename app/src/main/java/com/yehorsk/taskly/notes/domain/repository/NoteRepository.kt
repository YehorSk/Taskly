package com.yehorsk.taskly.notes.domain.repository

import com.yehorsk.taskly.core.domain.DataError
import com.yehorsk.taskly.core.domain.EmptyResult
import com.yehorsk.taskly.core.domain.Result
import com.yehorsk.taskly.notes.domain.models.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: String): Result<Note, DataError.Local>

    suspend fun insertNote(note: Note): EmptyResult<DataError.Local>

    suspend fun updateNote(note: Note): EmptyResult<DataError.Local>

    suspend fun deleteNote(note: Note): EmptyResult<DataError.Local>

}