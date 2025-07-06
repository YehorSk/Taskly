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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.taskly.R
import com.yehorsk.taskly.categories.utils.formatReadable
import com.yehorsk.taskly.categories.utils.select
import com.yehorsk.taskly.core.presentation.components.DateTimePicker
import com.yehorsk.taskly.core.presentation.components.TitleNavBar
import com.yehorsk.taskly.todos.domain.models.CategorySummary
import com.yehorsk.taskly.todos.presentation.add_edit_todo.components.SelectCategoryDialog
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
            onValueChange = { onAction(MainListScreenAction.OnTitleChanged(it)) },
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
            onValueChange = { onAction(MainListScreenAction.OnDescriptionChanged(it)) },
            placeholder = {
                Text(
                    text = stringResource(R.string.your_description),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        )
        Box(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
        ){
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = true) { expanded = true },
                readOnly = true,
                label = { Text(stringResource(R.string.category)) },
                value = "",
                onValueChange = {},
                trailingIcon = {
                    val icon = expanded.select(Icons.Filled.ArrowDropUp, Icons.Filled.ArrowDropDown)
                    Icon(imageVector = icon, "")
                },
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
                content = {
                    Text(
                        text = state.dueDate?.formatReadable() ?: stringResource(R.string.anytime),
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                onClick = { onAction(MainListScreenAction.OnShowDateTimePicker) }
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
            content = {
                Text(
                    text = "Add Task",
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            onClick = {  },
            enabled = true
        )
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
            var selectedIndex by remember { mutableIntStateOf(-1) }
            SelectCategoryDialog(
                label = "Sample",
                categories = state.categories,
                selectedIndex = selectedIndex,
                onSelect = { item -> selectedIndex = item.id },
                onDismissRequest = { expanded = false }
            )
        }
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
