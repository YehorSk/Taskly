package com.yehorsk.taskly.notes.presentation.add_edit_note

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.taskly.R
import com.yehorsk.taskly.categories.utils.toColor
import com.yehorsk.taskly.core.presentation.components.TitleNavBar
import com.yehorsk.taskly.core.utils.AddEditAction
import com.yehorsk.taskly.core.utils.brightColors
import com.yehorsk.taskly.notes.data.database.models.CheckItem
import com.yehorsk.taskly.notes.presentation.list.NoteListScreenAction
import com.yehorsk.taskly.notes.presentation.list.NoteListScreenUiState
import com.yehorsk.taskly.notes.presentation.list.NoteListScreenViewModel
import com.yehorsk.taskly.todos.presentation.list.MainListScreenAction
import com.yehorsk.taskly.ui.theme.TasklyTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddEditNoteListScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: NoteListScreenViewModel = koinViewModel(),
    onGoBackClicked: () -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    AddEditNoteListScreen(
        modifier = modifier,
        state = state,
        onAction = { action ->
            when(action){
                is NoteListScreenAction.OnGoBackClicked -> { onGoBackClicked() }
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )

}

@Composable
fun AddEditNoteListScreen(
    modifier: Modifier = Modifier,
    state: NoteListScreenUiState,
    onAction: (NoteListScreenAction) -> Unit
){
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            TitleNavBar(
                title = when(state.action){
                    AddEditAction.ADD -> R.string.new_note
                    AddEditAction.EDIT -> R.string.update_note
                },
                onGoBack = { onAction(NoteListScreenAction.OnGoBackClicked) },
                showGoBack = true
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 12.dp),
                value = state.title,
                onValueChange = { onAction(NoteListScreenAction.OnTitleChanged(it)) },
                placeholder = {
                    Text(
                        text = stringResource(R.string.your_title),
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                )
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                value = state.description,
                onValueChange = { onAction(NoteListScreenAction.OnDescriptionChanged(it)) },
                placeholder = {
                    Text(
                        text = stringResource(R.string.your_description),
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                )
            )
            state.checkItems.forEachIndexed { index, item ->
                Row(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            onAction(NoteListScreenAction.OnCheckItemDeleted(item))
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null
                            )
                        }
                    )
                    TextField(
                        modifier = Modifier
                            .padding(start = 8.dp),
                        value = item.name,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                        ),
                        placeholder = {
                            Text(
                                text = "Item"
                            )
                        },
                        onValueChange = {
                            onAction(NoteListScreenAction.OnCheckItemUpdated(CheckItem(item.id, it)))
                        },
                    )
                }
            }
            Text(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    .clickable{
                        onAction(NoteListScreenAction.OnCheckItemAdded)
                    },
                text = stringResource(R.string.add_new_item),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 32.dp),
                text = "Choose Color",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                brightColors.forEach { item ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(item.toColor())
                            .clickable{
                                onAction(NoteListScreenAction.OnColorChange(item))
                            }
                    ){
                        if(state.color == item){
                            Icon(
                                modifier = Modifier
                                    .fillMaxSize(),
                                tint = Color.White,
                                imageVector = Icons.Default.Check,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
            Button(
                modifier = Modifier
                    .padding(
                        top = 16.dp,
                        bottom = 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
                    .fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                content = {
                    Text(
                        text = when(state.action){
                            AddEditAction.ADD -> stringResource(R.string.add_note)
                            AddEditAction.EDIT -> stringResource(R.string.update_note)
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                onClick = {
                    when(state.action){
                        AddEditAction.ADD -> onAction(NoteListScreenAction.OnSaveClicked)
                        AddEditAction.EDIT -> onAction(NoteListScreenAction.OnUpdateClicked)
                    }
                    onAction(NoteListScreenAction.OnGoBackClicked)
                },
                enabled = state.title.isNotEmpty()
            )
            if(state.action == AddEditAction.EDIT && state.currentNote != null){
                Button(
                    modifier = Modifier
                        .padding(
                            bottom = 16.dp,
                            start = 16.dp,
                            end = 16.dp
                        )
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEF5350)
                    ),
                    content = {
                        Text(
                            text = stringResource(R.string.delete),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    onClick = {
                        onAction(NoteListScreenAction.OnDeleteClicked)
                        onAction(NoteListScreenAction.OnGoBackClicked)
                    },
                    enabled = true
                )
            }
        }
    }
}

@Preview
@Composable
fun AddNoteListScreenPreview(){
    TasklyTheme {
        AddEditNoteListScreen(
            state = NoteListScreenUiState(
                isLoading = false,
                checkItems = listOf(CheckItem(id = "","Test 1", isDone = false),CheckItem(id = "","Test 2", isDone = false)),
                action = AddEditAction.ADD
            ),
            onAction = {}
        )
    }
}

@Preview
@Composable
fun EditNoteListScreenPreview(){
    TasklyTheme {
        AddEditNoteListScreen(
            state = NoteListScreenUiState(
                isLoading = false,
                checkItems = listOf(CheckItem(id = "","Test 1", isDone = false),CheckItem(id = "","Test 2", isDone = false)),
                action = AddEditAction.EDIT
            ),
            onAction = {}
        )
    }
}