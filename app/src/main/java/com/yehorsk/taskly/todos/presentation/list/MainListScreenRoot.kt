package com.yehorsk.taskly.todos.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.taskly.R
import com.yehorsk.taskly.core.presentation.components.TitleNavBar
import com.yehorsk.taskly.todos.presentation.list.components.CustomDatePicker
import com.yehorsk.taskly.todos.presentation.list.components.ToDoList
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainListScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: MainListScreenViewModel = koinViewModel(),
    onItemClick: (Int) -> Unit
){

    MainListScreen(
        modifier = modifier
            .fillMaxSize(),
        viewModel = viewModel,
        onAction = { action ->
            when(action){
                is MainListScreenAction.OnItemClick -> { onItemClick(action.todo.id) }
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun MainListScreen(
    modifier: Modifier = Modifier,
    viewModel: MainListScreenViewModel,
    onAction: (MainListScreenAction) -> Unit
){

    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            TitleNavBar(
                title = R.string.my_tasks,
                onGoBack = {},
                showGoBack = false
            )
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