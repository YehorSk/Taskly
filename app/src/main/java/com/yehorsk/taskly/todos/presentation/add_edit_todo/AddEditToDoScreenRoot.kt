package com.yehorsk.taskly.todos.presentation.add_edit_todo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.yehorsk.taskly.categories.utils.formatReadable
import com.yehorsk.taskly.categories.utils.select
import com.yehorsk.taskly.core.presentation.components.DateTimePicker
import com.yehorsk.taskly.core.presentation.components.TitleNavBar
import com.yehorsk.taskly.core.utils.AddEditAction
import com.yehorsk.taskly.todos.presentation.add_edit_todo.components.SelectCategoryDialog
import com.yehorsk.taskly.todos.presentation.list.MainListScreenAction
import com.yehorsk.taskly.todos.presentation.list.MainListScreenUiState
import com.yehorsk.taskly.todos.presentation.MainToDoScreensViewModel
import com.yehorsk.taskly.ui.theme.TasklyTheme

@Composable
fun AddEditToDoScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: MainToDoScreensViewModel,
    onGoBackClicked: () -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    AddEditToDoScreen(
        modifier = modifier,
        state = state,
        onAction = { action ->
            when(action){
                is MainListScreenAction.OnGoBackClicked -> { onGoBackClicked() }
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun AddEditToDoScreen(
    modifier: Modifier = Modifier,
    state: MainListScreenUiState,
    onAction: (MainListScreenAction) -> Unit
){
    var expanded by remember { mutableStateOf(false) }
    var isNavigating by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            TitleNavBar(
                title = when(state.action){
                    AddEditAction.ADD -> R.string.new_task
                    AddEditAction.EDIT -> R.string.update_task
                },
                onGoBack = {
                    isNavigating = true
                    onAction(MainListScreenAction.OnGoBackClicked)
                           },
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
            if(state.isLoading){
                Box(
                    contentAlignment = Alignment.Center
                ){
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }else{
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp),
                    value = state.title,
                    onValueChange = { onAction(MainListScreenAction.OnTitleChanged(it)) },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.your_title),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    enabled = !isNavigating
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    value = state.description,
                    onValueChange = { onAction(MainListScreenAction.OnDescriptionChanged(it)) },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.your_description),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    enabled = !isNavigating
                )
                Box(
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                ){
                    val selectedCategory = state.categories.find { it.id == state.selectedCategory }
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = true) { expanded = true },
                        readOnly = true,
                        label = { Text(stringResource(R.string.category)+"*") },
                        value = if (state.selectedCategory == null || selectedCategory == null) {
                            stringResource(R.string.select)
                        } else {
                            selectedCategory.title
                        },
                        onValueChange = {},
                        trailingIcon = {
                            val icon = expanded.select(Icons.Filled.ArrowDropUp, Icons.Filled.ArrowDropDown)
                            Icon(imageVector = icon, "")
                        },
                        enabled = !isNavigating
                    )
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 8.dp)
                            .clip(MaterialTheme.shapes.extraSmall)
                            .clickable(enabled = true) { expanded = true },
                        color = Color.Transparent,
                    ) {  }
                }
                Row(
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                            bottom = 16.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .padding(end = 16.dp),
                        text = stringResource(R.string.due_date),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Button(
                        modifier = Modifier
                            .wrapContentWidth(),
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        content = {
                            Text(
                                text = state.dueDate?.formatReadable() ?: stringResource(R.string.anytime),
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        onClick = { onAction(MainListScreenAction.OnShowDateTimePicker) },
                        enabled = !isNavigating
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(
                            bottom = 16.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .padding(end = 16.dp),
                        text = stringResource(R.string.alarm),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Switch(
                        checked = state.alarmOn,
                        onCheckedChange = { onAction(MainListScreenAction.OnAlarmChanged(it)) },
                        thumbContent = if(state.alarmOn){
                            {
                                Icon(
                                    imageVector = Icons.Filled.Notifications,
                                    contentDescription = null,
                                    modifier = Modifier.size(SwitchDefaults.IconSize),
                                )
                            }
                        } else {
                            {
                                Icon(
                                    imageVector = Icons.Filled.NotificationsNone,
                                    contentDescription = null,
                                    modifier = Modifier.size(SwitchDefaults.IconSize),
                                )
                            }
                        },
                        enabled = !isNavigating
                    )
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
                                AddEditAction.ADD -> stringResource(R.string.add_task)
                                AddEditAction.EDIT -> stringResource(R.string.update_task)
                            },
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    onClick = {
                        when(state.action){
                            AddEditAction.ADD -> onAction(MainListScreenAction.OnSaveClicked)
                            AddEditAction.EDIT -> onAction(MainListScreenAction.OnUpdateClicked)
                        }
                        onAction(MainListScreenAction.OnGoBackClicked)
                    },
                    enabled = state.title.isNotEmpty() && state.selectedCategory != null && !isNavigating
                )
                if(state.action == AddEditAction.EDIT && state.currentToDo != null){
                    Button(
                        modifier = Modifier
                            .padding(
                                bottom = 16.dp,
                                start = 16.dp,
                                end = 16.dp
                            )
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(4.dp),
                        content = {
                            Text(
                                text = stringResource(R.string.complete),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        enabled = !state.currentToDo.isDone,
                        onClick = {
                            onAction(MainListScreenAction.OnIsDoneClicked(todo = state.selectedToDo!!))
                            onAction(MainListScreenAction.OnGoBackClicked)
                        }
                    )
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
                            onAction(MainListScreenAction.OnDeleteClicked)
                            onAction(MainListScreenAction.OnGoBackClicked)
                        },
                        enabled = true
                    )
                }
                DateTimePicker(
                    showDatePicker = state.showDateTimePicker,
                    title = R.string.due_date,
                    startDate = state.dueDate,
                    onDateChangeListener = {
                        onAction(MainListScreenAction.OnDueDateChanged(it))
                        onAction(MainListScreenAction.OnHideDateTimePicker)
                    },
                    onDismiss = {
                        onAction(MainListScreenAction.OnHideDateTimePicker)
                    }
                )
                if(expanded){
                    SelectCategoryDialog(
                        label = stringResource(R.string.category),
                        categories = state.categories,
                        selectedIndex = state.selectedCategory ?: 0,
                        onSelect = { item ->
                            onAction(MainListScreenAction.OnSelectedCategoryChange(item.id))
                            expanded = false
                        },
                        onDismissRequest = { expanded = false }
                    )
                }
            }
        }
    }

}

@Preview
@Composable
fun AddEditToDoScreenPreview(){
    TasklyTheme {
        AddEditToDoScreen(
            state = MainListScreenUiState(isLoading = false),
            onAction = {}
        )
    }
}
