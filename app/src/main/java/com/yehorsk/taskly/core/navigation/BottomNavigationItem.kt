package com.yehorsk.taskly.core.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notes
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Task
import androidx.compose.material3.Icon
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
        selectedIcon = Icons.Default.CheckCircle,
        unselectedIcon = Icons.Outlined.Task
    )

    object Categories : BottomNavigationItem(
        route = "CATEGORIES",
        title = R.string.categories,
        selectedIcon = Icons.Default.Menu,
        unselectedIcon = Icons.Outlined.Category,
    )

    object Notes : BottomNavigationItem(
        route = "NOTES",
        title = R.string.notes,
        selectedIcon = Icons.Default.Menu,
        unselectedIcon = Icons.Outlined.Notes,
    )

    object Profile : BottomNavigationItem(
        route = "PROFILE",
        title = R.string.profile,
        selectedIcon = Icons.Default.Person,
        unselectedIcon = Icons.Outlined.Person
    )

    companion object {
        val items = listOf(Tasks, Categories, Notes, Profile)
    }
}