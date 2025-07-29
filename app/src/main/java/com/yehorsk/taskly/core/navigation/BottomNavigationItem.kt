package com.yehorsk.taskly.core.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Notes
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Task
import androidx.compose.ui.graphics.vector.ImageVector
import com.yehorsk.taskly.R

sealed class BottomNavigationItem(
    val route: String,
    @StringRes val title: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
) {
    object Tasks : BottomNavigationItem(
        route = "TODOS",
        title = R.string.my_tasks,
        selectedIcon = Icons.Default.Task,
        unselectedIcon = Icons.Outlined.Task
    )

    object Categories : BottomNavigationItem(
        route = "CATEGORIES",
        title = R.string.categories,
        selectedIcon = Icons.Default.Category,
        unselectedIcon = Icons.Outlined.Category,
    )

    object Notes : BottomNavigationItem(
        route = "NOTES",
        title = R.string.notes,
        selectedIcon = Icons.Default.Notes,
        unselectedIcon = Icons.Outlined.Notes,
    )

    object Settings : BottomNavigationItem(
        route = "SETTINGS",
        title = R.string.settings,
        selectedIcon = Icons.Default.Settings,
        unselectedIcon = Icons.Outlined.Settings
    )

    companion object {
        val items = listOf(Tasks, Categories, Notes, Settings)
    }
}