package com.yehorsk.taskly.di

import com.yehorsk.taskly.categories.presentation.list.CategoryScreenViewModel
import com.yehorsk.taskly.notes.presentation.list.NoteListScreenViewModel
import com.yehorsk.taskly.todos.presentation.list.MainListScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {

    viewModelOf(::CategoryScreenViewModel)
    viewModelOf(::MainListScreenViewModel)
    viewModelOf(::NoteListScreenViewModel)

}