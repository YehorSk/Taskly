package com.yehorsk.taskly.todos.presentation.add_edit_todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.taskly.R
import com.yehorsk.taskly.core.presentation.components.TitleNavBar
import com.yehorsk.taskly.todos.presentation.list.MainListScreenAction
import com.yehorsk.taskly.todos.presentation.list.MainListScreenUiState
import com.yehorsk.taskly.todos.presentation.list.MainListScreenViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddEditToDoScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: MainListScreenViewModel = koinViewModel(),
    onGoBackClicked: () -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    AddEditToDoScreen(
        modifier = modifier,
        state = state,
        onAction = { action ->
            when(action){
                is MainListScreenAction.OnGoBackClicked -> { onGoBackClicked() }
                else -> viewModel::onAction
            }
        }
    )

}

@Composable
fun AddEditToDoScreen(
    modifier: Modifier = Modifier,
    state: MainListScreenUiState,
    onAction: (MainListScreenAction) -> Unit
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        TitleNavBar(
            title = R.string.new_task,
            onGoBack = { onAction(MainListScreenAction.OnGoBackClicked) },
            showGoBack = true
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp),
            value = state.title,
            onValueChange = {  },
            placeholder = {
                Text(
                    text = stringResource(R.string.your_title),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp),
            value = state.description,
            onValueChange = {  },
            placeholder = {
                Text(
                    text = stringResource(R.string.your_description),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        )
        Button(
            modifier = Modifier
                .padding(
                    top = 16.dp,
                    bottom = 16.dp,
                    start = 16.dp,
                    end = 16.dp
                )
                .fillMaxWidth(),
            content = {
                Text(
                    text = "Add Task",
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            onClick = {  },
            enabled = true
        )
    }
}

@Preview
@Composable
fun AddEditToDoScreenPreview(){
    AddEditToDoScreen(
        state = MainListScreenUiState(),
        onAction = {}
    )
}
