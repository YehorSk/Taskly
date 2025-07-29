package com.yehorsk.taskly.todos.data.repository

import androidx.sqlite.SQLiteException
import com.yehorsk.taskly.core.domain.DataError
import com.yehorsk.taskly.core.domain.EmptyResult
import com.yehorsk.taskly.core.domain.Result
import com.yehorsk.taskly.core.domain.alarm.AlarmScheduler
import com.yehorsk.taskly.core.domain.onSuccess
import com.yehorsk.taskly.todos.data.database.dao.ToDoDao
import com.yehorsk.taskly.todos.domain.models.CategorySummary
import com.yehorsk.taskly.todos.data.mappers.toToDo
import com.yehorsk.taskly.todos.data.mappers.toToDoEntity
import com.yehorsk.taskly.todos.domain.models.ToDo
import com.yehorsk.taskly.todos.domain.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.collections.map

class ToDoRepositoryImpl(
    val toDoDao: ToDoDao,
    val alarmScheduler: AlarmScheduler
): ToDoRepository {

    override fun getCategorySummaries(): Flow<List<CategorySummary>> {
        return toDoDao.getCategorySummaries()
    }

    override fun getTodos(dates: List<LocalDate>): Flow<List<ToDo>> {
        return if(dates.isEmpty()){
            Timber.d("Not Empty List")
            toDoDao.getAllTodos().map { data ->
                data.map {
                    it.toToDo()
                }
            }
        }else{
            Timber.d("Empty List")
            toDoDao.getTodosByDate(dates).map { data ->
                data.map {
                    it.toToDo()
                }
            }
        }
    }

    override suspend fun getTodosSuspend(): List<ToDo> {
        return toDoDao.getTodosSuspend().map { it.toToDo() }
    }

    override suspend fun getToDoById(id: String): Result<ToDo, DataError.Local> {
        val result = toDoDao.getToDoById(id).toToDo()
        return Result.Success(result)
    }

    override suspend fun insertTodo(toDo: ToDo): EmptyResult<DataError.Local> {
        return try{
            toDoDao.insertTodo(toDo.toToDoEntity())
            Result.Success(Unit)
        }catch (e: SQLiteException){
            Result.Error(DataError.Local.DISK_FULL)
        }.onSuccess {
            if(toDo.alarmOn) alarmScheduler.schedule(toDo)
        }
    }

    override suspend fun updateTodo(toDo: ToDo): EmptyResult<DataError.Local> {
        return try{
            toDoDao.updateTodo(toDo.toToDoEntity())
            Result.Success(Unit)
        }catch (e: SQLiteException){
            Result.Error(DataError.Local.DISK_FULL)
        }.onSuccess {
            if(toDo.alarmOn) alarmScheduler.schedule(toDo)
            else alarmScheduler.cancel(toDo)
        }
    }

    override suspend fun deleteTodo(toDo: ToDo): EmptyResult<DataError.Local> {
        return try{
            toDoDao.deleteTodo(toDo.toToDoEntity())
            Result.Success(Unit)
        }catch (e: SQLiteException){
            Result.Error(DataError.Local.DISK_FULL)
        }.onSuccess {
            alarmScheduler.cancel(toDo)
        }
    }

    override suspend fun rescheduleAlarms() = withContext(Dispatchers.IO){
        toDoDao.getTodosSuspend()
            .filter { todo ->
                todo.alarmOn && todo.dueDate?.isAfter(LocalDateTime.now()) == true
            }
            .forEach { alarmScheduler.schedule(it.toToDo()) }
    }

    override suspend fun onDone(todo: ToDo): EmptyResult<DataError.Local> {
        return try{
            toDoDao.onDone(todo.id.toString())
            Result.Success(Unit)
        }catch (e: SQLiteException){
            Result.Error(DataError.Local.DISK_FULL)
        }.onSuccess {
            alarmScheduler.cancel(todo)
        }
    }
}