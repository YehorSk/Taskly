package com.yehorsk.taskly.di

import android.app.Application
import androidx.room.Room
import com.yehorsk.taskly.categories.data.database.dao.CategoryDao
import com.yehorsk.taskly.core.data.database.TasklyDatabase
import org.koin.dsl.module

fun provideDatabase(application: Application): TasklyDatabase =
    Room.databaseBuilder(
        application,
        TasklyDatabase::class.java,
        "taskly_database"
    ).fallbackToDestructiveMigration(true).build()

fun provideCategoryDao(tasklyDatabase: TasklyDatabase): CategoryDao = tasklyDatabase.getCategoryDao()

val databaseModule = module {

    single { provideDatabase(get()) }
    single { provideCategoryDao(get()) }

}