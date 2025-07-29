package com.yehorsk.taskly.core.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.yehorsk.taskly.categories.presentation.list.CategoryScreenRoot
import com.yehorsk.taskly.notes.presentation.add_edit_note.AddEditNoteListScreenRoot
import com.yehorsk.taskly.notes.presentation.list.NoteListScreenAction
import com.yehorsk.taskly.notes.presentation.list.NoteListScreenRoot
import com.yehorsk.taskly.notes.presentation.list.NoteListScreenViewModel
import com.yehorsk.taskly.todos.presentation.add_edit_todo.AddEditToDoScreenRoot
import com.yehorsk.taskly.todos.presentation.list.ToDoListScreenRoot
import com.yehorsk.taskly.todos.presentation.list.MainListScreenAction
import com.yehorsk.taskly.todos.presentation.MainToDoScreensViewModel
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
                navController = navController,
                modifier = modifier
            )
        }
        composable(
            route = Route.ToDos.route
        ) {
            ToDoListScreenRoot(
                navController = navController,
                onItemClick = { id -> navController.navigate(Route.AddEditTodo(id = id.toString())){launchSingleTop = true} }
            )
        }
        composable(
            route = Route.Notes.route
        ) {
            NoteListScreenRoot(
                navController = navController,
                onItemClick = { id -> navController.navigate(Route.AddEditNote(id = id.toString())){launchSingleTop = true} }
            )
        }
        composable(
            route = Route.Settings.route
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
        composable<Route.AddEditTodo>() {
            val args = it.toRoute<Route.AddEditTodo>()
            val viewModel: MainToDoScreensViewModel = koinViewModel()
            LaunchedEffect(args.id) {
                args.id?.toIntOrNull()?.let { id ->
                    viewModel.onAction(MainListScreenAction.OnGetToDoById(id))
                } ?: run {
                    viewModel.onAction(MainListScreenAction.OnAddNewToDoClicked)
                }
            }
            AddEditToDoScreenRoot(
                viewModel = viewModel,
                onGoBackClicked = { navController.navigateUp() }
            )
        }
        composable<Route.AddEditNote> {
            val args = it.toRoute<Route.AddEditNote>()
            val viewModel: NoteListScreenViewModel = koinViewModel()
            LaunchedEffect(args.id) {
                args.id?.toIntOrNull()?.let { id ->
                    viewModel.onAction(NoteListScreenAction.OnGetNoteById(args.id.toInt()))
                } ?: run {
                    viewModel.onAction(NoteListScreenAction.OnAddNewNoteClicked)
                }
            }
            AddEditNoteListScreenRoot(
                viewModel = viewModel,
                onGoBackClicked = { navController.navigateUp() }
            )
        }
    }
}