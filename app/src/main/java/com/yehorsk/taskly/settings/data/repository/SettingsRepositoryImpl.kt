package com.yehorsk.taskly.settings.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.yehorsk.taskly.core.data.datastore.LANGUAGE_KEY
import com.yehorsk.taskly.core.data.datastore.THEME_KEY
import com.yehorsk.taskly.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>
): SettingsRepository {

    override val darkModeFlow: Flow<Boolean> = dataStore.data
        .map { prefs -> prefs[THEME_KEY] ?: false }


    override val languageFlow: Flow<String> = dataStore.data
        .map { prefs -> prefs[LANGUAGE_KEY] ?: "en" }

    override suspend fun setDarkMode(enabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[THEME_KEY] = enabled
        }
    }

    override suspend fun setLanguage(language: String) {
        dataStore.edit { prefs ->
            prefs[LANGUAGE_KEY] = language
        }
    }


}