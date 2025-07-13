package com.yehorsk.taskly.todos.presentation.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.taskly.todos.domain.models.ToDo
import com.yehorsk.taskly.todos.presentation.list.components.CustomDatePicker
import com.yehorsk.taskly.todos.presentation.list.components.ToDoList

@Composable
fun ListScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: MainListScreenViewModel,
    onItemClick: (ToDo) -> Unit
){

    val state by viewModel.state.collectAsStateWithLifecycle()

    TodayListScreen(
        modifier = modifier,
        state = state,
        onAction = { action ->
            when(action){
                is MainListScreenAction.OnItemClick -> { onItemClick(action.todo) }
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun TodayListScreen(
    modifier: Modifier = Modifier,
    state: MainListScreenUiState,
    onAction: (MainListScreenAction) -> Unit
){
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        CustomDatePicker(
            selectedDate = state.selectedDate,
            onDateChange = { onAction(MainListScreenAction.OnSelectedDateChanged(it)) }
        )
        ToDoList(
            modifier = Modifier.fillMaxSize(),
            items = state.items,
            onItemClick = { onAction(MainListScreenAction.OnItemClick(it)) }
        )
    }
}