package com.yehorsk.taskly.settings.presentation

sealed interface SettingsScreenAction {

    data class OnThemeChanged(val theme: Boolean): SettingsScreenAction

    data class OnLanguageChanged(val lang: String): SettingsScreenAction

}