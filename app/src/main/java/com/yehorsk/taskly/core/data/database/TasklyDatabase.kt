package com.yehorsk.taskly.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yehorsk.taskly.categories.data.database.dao.CategoryDao
import com.yehorsk.taskly.categories.data.database.models.CategoryEntity
import com.yehorsk.taskly.todos.data.database.dao.ToDoDao
import com.yehorsk.taskly.todos.data.database.models.ToDoEntity

@Database(entities = [CategoryEntity::class, ToDoEntity::class], version = 1)
abstract class TasklyDatabase: RoomDatabase() {

    abstract val categoryDao: CategoryDao
    abstract val todoDao: ToDoDao

}