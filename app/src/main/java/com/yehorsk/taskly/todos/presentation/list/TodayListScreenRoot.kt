package com.yehorsk.taskly.todos.presentation.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.taskly.todos.domain.models.ToDo
import com.yehorsk.taskly.todos.presentation.list.components.ToDoList
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TodayListScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: MainListScreenViewModel,
    onItemClick: (ToDo) -> Unit
){

    val state by viewModel.state.collectAsStateWithLifecycle()

    TodayListScreen(
        modifier = modifier,
        state = state,
        onAction = { viewModel::onAction }
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
        ToDoList(
            modifier = Modifier.fillMaxSize(),
            items = state.items,
            onItemClick = {}
        )
    }
}