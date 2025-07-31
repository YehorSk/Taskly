package com.yehorsk.taskly.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.taskly.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsScreenViewModel(
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

    fun onAction(action: SettingsScreenAction){
        when(action){
            is SettingsScreenAction.OnLanguageChanged -> setLanguage(action.lang)
            is SettingsScreenAction.OnThemeChanged -> setDarkMode(action.theme)
        }
    }

    fun setDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.setDarkMode(enabled)
        }
    }

    fun setLanguage(lang: String) {
        viewModelScope.launch {
            settingsRepository.setLanguage(lang)
        }
    }

}