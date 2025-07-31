package com.yehorsk.taskly.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.yehorsk.taskly.core.data.datastore.createDataStore
import org.koin.dsl.module

val dataStoreModule = module {
    single<DataStore<Preferences>> { createDataStore(get()) }
}