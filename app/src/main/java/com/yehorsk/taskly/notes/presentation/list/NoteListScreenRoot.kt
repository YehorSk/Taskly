package com.yehorsk.taskly.notes.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.yehorsk.taskly.notes.presentation.list.components.NoteList
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NoteListScreenRoot(
    viewModel: NoteListScreenViewModel = koinViewModel(),
    navController: NavHostController,
    onItemClick: (Int) -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    NoteListScreen(
        state = state,
        onAction = { action ->
            when(action){
                is NoteListScreenAction.OnItemClick -> { onItemClick(action.note.id) }
                is NoteListScreenAction.OnFABClicked -> { navController.navigate(Route.AddEditNote()) }
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
fun NoteListScreen(
    modifier: Modifier = Modifier,
    state: NoteListScreenUiState,
    onAction: (NoteListScreenAction) -> Unit,
    bottomBar: @Composable() () -> Unit,
){
    Scaffold(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            TitleNavBar(
                title = R.string.my_notes,
                onGoBack = {},
                showGoBack = false
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onAction(NoteListScreenAction.OnFABClicked) }) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        },
        bottomBar = {
            bottomBar()
        }
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NoteList(
                modifier = Modifier
                    .fillMaxSize(),
                items = state.items,
                onItemClick = { onAction(NoteListScreenAction.OnItemClick(it)) }
            )
        }
    }
}