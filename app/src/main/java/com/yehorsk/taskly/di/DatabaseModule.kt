package com.yehorsk.taskly.di

import androidx.room.Room
import com.yehorsk.taskly.core.data.database.TasklyDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    single<TasklyDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            TasklyDatabase::class.java,
            "taskly_database"
        ).build()
    }

    single {
        get<TasklyDatabase>().categoryDao
    }

    single {
        get<TasklyDatabase>().todoDao
    }

    single {
        get<TasklyDatabase>().noteDao
    }

}