package com.yehorsk.taskly.core.presentation.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.yehorsk.taskly.core.navigation.BottomNavigationItem

@Composable
fun RowScope.AddItem(
    screen: BottomNavigationItem,
    currentDestination: NavDestination?,
    navController: NavHostController,
){

    val isSelected = currentDestination?.hierarchy?.any{
        it.route == screen.route
    } == true

    NavigationBarItem(
        label = {
            AutoResizedText(
                text = stringResource(screen.title),
                style = MaterialTheme.typography.labelSmall
            )
        },
        icon = {
            Icon(
                imageVector = if (isSelected) screen.selectedIcon else screen.unselectedIcon,
                contentDescription = stringResource(screen.title)
            )
        },
        selected = isSelected,
        onClick = {
            if(!isSelected){
                navController.navigate(screen.route){
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }
        }
    )
}