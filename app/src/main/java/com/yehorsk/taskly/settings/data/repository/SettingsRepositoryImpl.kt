package com.yehorsk.taskly.settings.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.yehorsk.taskly.core.data.datastore.IS_24_HOUR_FORMAT_KEY
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

    override val hourFormatFlow: Flow<Boolean> = dataStore.data
        .map { prefs -> prefs[IS_24_HOUR_FORMAT_KEY] ?: true }

    override suspend fun setDarkMode(enabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[THEME_KEY] = enabled
        }
    }

    override suspend fun setHourFormat(format: Boolean) {
        dataStore.edit { prefs ->
            prefs[IS_24_HOUR_FORMAT_KEY] = format
        }
    }

    override suspend fun setLanguage(language: String) {
        dataStore.edit { prefs ->
            prefs[LANGUAGE_KEY] = language
        }
    }


}