package com.yehorsk.taskly

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.taskly.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ThemeViewModel(
    private val settingsRepository: SettingsRepository
): ViewModel() {

    val darkMode: StateFlow<Boolean> = settingsRepository
        .darkModeFlow
        .stateIn(viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            false)

    val language: StateFlow<String> = settingsRepository
        .languageFlow
        .stateIn(viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            "en")

}