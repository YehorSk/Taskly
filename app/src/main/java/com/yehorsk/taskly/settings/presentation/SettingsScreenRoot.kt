package com.yehorsk.taskly.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.yehorsk.taskly.R
import com.yehorsk.taskly.core.presentation.components.BottomBar
import com.yehorsk.taskly.core.presentation.components.TitleNavBar
import com.yehorsk.taskly.settings.presentation.component.SwitchListItem
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreenRoot(
    navController: NavHostController,
    viewModel: SettingsScreenViewModel = koinViewModel()
){
    val theme by viewModel.darkMode.collectAsStateWithLifecycle()
    val language by viewModel.language.collectAsStateWithLifecycle()

    SettingsScreen(
        theme = theme,
        language = language,
        bottomBar = {
            BottomBar(
                navController = navController
            )
        },
        onAction = viewModel::onAction
    )
}

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    theme: Boolean,
    language: String,
    bottomBar: @Composable() () -> Unit,
    onAction: (SettingsScreenAction) -> Unit
){
    Scaffold(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            TitleNavBar(
                title = R.string.my_tasks,
                onGoBack = {},
                showGoBack = false
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        },
        bottomBar = {
            bottomBar()
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            item {
                SwitchListItem(
                    checked = theme,
                    iconChecked = Icons.Filled.LightMode,
                    iconUnchecked = Icons.Filled.DarkMode,
                    onSwitched = { onAction(SettingsScreenAction.OnThemeChanged(it)) }
                )
            }
        }
    }
}