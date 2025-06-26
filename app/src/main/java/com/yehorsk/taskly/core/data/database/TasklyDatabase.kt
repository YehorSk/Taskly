package com.yehorsk.taskly.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yehorsk.taskly.categories.data.database.dao.CategoryDao
import com.yehorsk.taskly.categories.data.database.models.CategoryEntity

@Database(entities = [(CategoryEntity::class)], version = 1)
abstract class TasklyDatabase: RoomDatabase() {

    abstract val categoryDao: CategoryDao

}