package com.yehorsk.taskly.settings.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    val darkModeFlow: Flow<Boolean>

    val languageFlow: Flow<String>

    suspend fun setDarkMode(enabled: Boolean)

    suspend fun setLanguage(language: String)

}