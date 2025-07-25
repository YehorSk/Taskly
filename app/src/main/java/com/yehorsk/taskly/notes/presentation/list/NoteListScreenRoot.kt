package com.yehorsk.taskly.notes.presentation.list

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
import com.yehorsk.taskly.notes.presentation.list.components.NoteList
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NoteListScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: NoteListScreenViewModel = koinViewModel(),
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    NoteListScreen(
        modifier = modifier,
        state = state
    )

}

@Composable
fun NoteListScreen(
    modifier: Modifier = Modifier,
    state: NoteListScreenUiState
){
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            TitleNavBar(
                title = R.string.my_notes,
                onGoBack = {},
                showGoBack = false
            )
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
                items = state.items
            ) { }
        }
    }
}