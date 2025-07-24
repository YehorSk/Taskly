package com.yehorsk.taskly.notes.data.repository

import androidx.sqlite.SQLiteException
import com.yehorsk.taskly.core.domain.DataError
import com.yehorsk.taskly.core.domain.EmptyResult
import com.yehorsk.taskly.core.domain.Result
import com.yehorsk.taskly.notes.data.database.dao.NoteDao
import com.yehorsk.taskly.notes.data.mappers.toEntity
import com.yehorsk.taskly.notes.data.mappers.toNote
import com.yehorsk.taskly.notes.domain.models.Note
import com.yehorsk.taskly.notes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    val noteDao: NoteDao
): NoteRepository {

    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes().map { data ->
            data.map { it.toNote() }
        }
    }

    override suspend fun getNoteById(id: String): Result<Note, DataError.Local> {
        val result = noteDao.getNoteById(id).toNote()
        return Result.Success(result)
    }

    override suspend fun insertNote(note: Note): EmptyResult<DataError.Local> {
        return try{
            noteDao.insertNote(note.toEntity())
            Result.Success(Unit)
        }catch (e: SQLiteException){
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun updateNote(note: Note): EmptyResult<DataError.Local> {
        return try{
            noteDao.updateNote(note.toEntity())
            Result.Success(Unit)
        }catch (e: SQLiteException){
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteNote(note: Note): EmptyResult<DataError.Local> {
        return try{
            noteDao.deleteNote(note.toEntity())
            Result.Success(Unit)
        }catch (e: SQLiteException){
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

}