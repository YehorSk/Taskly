package com.yehorsk.taskly.di

import com.yehorsk.taskly.categories.data.repository.CategoryRepositoryImpl
import com.yehorsk.taskly.categories.domain.repository.CategoryRepository
import com.yehorsk.taskly.notes.data.repository.NoteRepositoryImpl
import com.yehorsk.taskly.notes.domain.repository.NoteRepository
import com.yehorsk.taskly.settings.data.repository.SettingsRepositoryImpl
import com.yehorsk.taskly.settings.domain.repository.SettingsRepository
import com.yehorsk.taskly.todos.data.repository.ToDoRepositoryImpl
import com.yehorsk.taskly.todos.domain.repository.ToDoRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<CategoryRepository> { CategoryRepositoryImpl(get()) }
    single<ToDoRepository> { ToDoRepositoryImpl(get(), get()) }
    single<NoteRepository> { NoteRepositoryImpl(get()) }
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
}