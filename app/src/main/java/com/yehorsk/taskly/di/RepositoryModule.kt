package com.yehorsk.taskly.di

import com.yehorsk.taskly.categories.data.repository.CategoryRepositoryImpl
import com.yehorsk.taskly.categories.domain.repository.CategoryRepository
import com.yehorsk.taskly.todos.data.repository.ToDoRepositoryImpl
import com.yehorsk.taskly.todos.domain.repository.ToDoRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<CategoryRepository> { CategoryRepositoryImpl(get()) }
    single<ToDoRepository> { ToDoRepositoryImpl(get()) }
}