package com.yehorsk.taskly.todos.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.yehorsk.taskly.R
import com.yehorsk.taskly.core.navigation.Route
import com.yehorsk.taskly.core.presentation.components.BottomBar
import com.yehorsk.taskly.core.presentation.components.TitleNavBar
import com.yehorsk.taskly.todos.presentation.list.components.CustomDatePicker
import com.yehorsk.taskly.todos.presentation.list.components.ToDoList
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ToDoListScreenRoot(
    viewModel: MainListScreenViewModel = koinViewModel(),
    navController: NavHostController,
    onItemClick: (Int) -> Unit
){

    ToDoListScreen(
        viewModel = viewModel,
        onAction = { action ->
            when(action){
                is MainListScreenAction.OnItemClick -> { onItemClick(action.todo.id) }
                is MainListScreenAction.OnFABClicked -> { navController.navigate(Route.AddEditTodo()) }
                else -> Unit
            }
            viewModel.onAction(action)
        },
        bottomBar = {
            BottomBar(
                navController = navController
            )
        }
    )
}

@Composable
fun ToDoListScreen(
    modifier: Modifier = Modifier,
    viewModel: MainListScreenViewModel,
    bottomBar: @Composable() () -> Unit,
    onAction: (MainListScreenAction) -> Unit
){

    val state by viewModel.state.collectAsStateWithLifecycle()

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
            FloatingActionButton(onClick = { onAction(MainListScreenAction.OnFABClicked) }) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        },
        bottomBar = {
            bottomBar()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            CustomDatePicker(
                selectedDates = state.selectedDates,
                fullCalendar = state.openFullCalendar,
                onDateChange = { onAction(MainListScreenAction.OnSelectedDateChanged(it)) },
                onFullCalendarClick = { onAction(MainListScreenAction.OnFullCalendarClicked) }
            )
            ToDoList(
                modifier = Modifier
                    .fillMaxSize(),
                items = state.sectionedToDos,
                onItemClick = { onAction(MainListScreenAction.OnItemClick(it)) },
                onIsDoneClick = { onAction(MainListScreenAction.OnIsDoneClicked(it))  }
            )
        }
    }
}