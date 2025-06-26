package com.yehorsk.taskly.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yehorsk.taskly.categories.presentation.list.CategoryScreenRoot

@Composable
fun NavigationRoot(
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = Route.Categories
    ){
        composable<Route.Categories> {
            CategoryScreenRoot()
        }
    }
}