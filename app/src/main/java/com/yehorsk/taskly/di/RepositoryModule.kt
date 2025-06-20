package com.yehorsk.taskly.di

import com.yehorsk.taskly.categories.data.repository.CategoryRepositoryImpl
import com.yehorsk.taskly.categories.domain.repository.CategoryRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<CategoryRepository> { CategoryRepositoryImpl(get()) }
}