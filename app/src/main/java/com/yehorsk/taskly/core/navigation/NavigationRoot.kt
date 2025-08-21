package com.yehorsk.taskly.core.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.yehorsk.taskly.categories.presentation.list.CategoryScreenRoot
import com.yehorsk.taskly.core.presentation.components.MainScaffold
import com.yehorsk.taskly.core.utils.ObserveAsEvents
import com.yehorsk.taskly.core.utils.SnackbarController
import com.yehorsk.taskly.notes.presentation.add_edit_note.AddEditNoteListScreenRoot
import com.yehorsk.taskly.notes.presentation.list.NoteListScreenAction
import com.yehorsk.taskly.notes.presentation.list.NoteListScreenRoot
import com.yehorsk.taskly.notes.presentation.list.NoteListScreenViewModel
import com.yehorsk.taskly.settings.presentation.SettingsScreenRoot
import com.yehorsk.taskly.todos.presentation.add_edit_todo.AddEditToDoScreenRoot
import com.yehorsk.taskly.todos.presentation.list.ToDoListScreenRoot
import com.yehorsk.taskly.todos.presentation.list.MainListScreenAction
import com.yehorsk.taskly.todos.presentation.MainToDoScreensViewModel
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    navController: NavHostController
){

    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    ObserveAsEvents(
        flow = SnackbarController.events,
        snackbarHostState
    ) { event ->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            val result = snackbarHostState.showSnackbar(
                message = event.message.asString(context),
                actionLabel = event.action?.name,
                duration = SnackbarDuration.Short,
                withDismissAction = true
            )
            if(result == SnackbarResult.ActionPerformed){
                event.action?.action?.invoke()
            }
        }
    }

    MainScaffold(
        modifier = modifier,
        snackbarHostState = snackbarHostState,
        navController = navController,
        content = {
            NavHost(
                navController = navController,
                startDestination = Route.ToDos.route
            ){
                composable(
                    route = Route.Categories.route
                ) {
                    CategoryScreenRoot(
                        onGoBackClicked = { navController.navigateUp() }
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
                    SettingsScreenRoot(
                        navController = navController
                    )
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
    )
}