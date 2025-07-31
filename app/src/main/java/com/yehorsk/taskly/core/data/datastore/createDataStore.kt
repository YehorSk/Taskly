package com.yehorsk.taskly.core.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile

fun createDataStore(context: Context): DataStore<Preferences>{
    return PreferenceDataStoreFactory.create(
        produceFile = {
            context.preferencesDataStoreFile(DATA_STORE_FILE_NAME)
        }
    )
}

const val DATA_STORE_FILE_NAME = "prefs.preferences.db"
val THEME_KEY = booleanPreferencesKey("dark_mode")
val LANGUAGE_KEY = stringPreferencesKey("language")