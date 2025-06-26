package com.yehorsk.taskly.di

import com.yehorsk.taskly.categories.presentation.list.CategoryScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {

    viewModelOf(::CategoryScreenViewModel)

}