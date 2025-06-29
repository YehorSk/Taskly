package com.yehorsk.taskly.core.presentation.components

import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.yehorsk.taskly.core.navigation.BottomNavigationItem
import com.yehorsk.taskly.core.navigation.BottomNavigationItem.Companion.items

@Composable
fun BottomBar(
    navController: NavHostController
){

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = items.any { it.route == currentDestination?.route}

    if(bottomBarDestination){
        NavigationBar {
            items.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }

}