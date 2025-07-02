package com.yehorsk.taskly.core.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.yehorsk.taskly.categories.presentation.list.CategoryScreenRoot
import com.yehorsk.taskly.todos.presentation.add_edit_todo.AddEditToDoScreenRoot
import com.yehorsk.taskly.todos.presentation.list.MainListScreen
import com.yehorsk.taskly.todos.presentation.list.MainListScreenViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = Route.ToDos.route
    ){
        composable(
            route = Route.Categories.route
        ) {
            CategoryScreenRoot(
                modifier = modifier
            )
        }
        composable(
            route = Route.ToDos.route
        ) {
            MainListScreen(
                modifier = modifier
            )
        }
        composable(
            route = Route.Notes.route
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Notes"
                )
            }
        }
        composable(
            route = Route.Profile.route
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Profile"
                )
            }
        }
        composable(
            Route.AddTodo.route
        ) {
            val viewModel: MainListScreenViewModel = koinViewModel()
            AddEditToDoScreenRoot(
                modifier = modifier,
                viewModel = viewModel,
                onGoBackClicked = { navController.popBackStack() }
            )
        }
    }
}