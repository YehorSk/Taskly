package com.yehorsk.taskly.todos.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.yehorsk.taskly.R
import com.yehorsk.taskly.core.navigation.Route
import com.yehorsk.taskly.core.presentation.components.TitleNavBar
import com.yehorsk.taskly.todos.domain.models.CategorySummary
import com.yehorsk.taskly.todos.presentation.MainToDoScreensViewModel
import com.yehorsk.taskly.todos.presentation.list.components.CategoryFilter
import com.yehorsk.taskly.todos.presentation.list.components.CustomDatePicker
import com.yehorsk.taskly.todos.presentation.list.components.ToDoList
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ToDoListScreenRoot(
    viewModel: MainToDoScreensViewModel = koinViewModel(),
    navController: NavHostController,
    onItemClick: (Int) -> Unit
){

    val hourFormat by viewModel.hourFormat.collectAsStateWithLifecycle()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val selectedFilter by viewModel.selectedCategories.collectAsStateWithLifecycle()

    ToDoListScreen(
        state = state,
        hourFormat = hourFormat,
        selectedFilter = selectedFilter,
        onAction = { action ->
            when(action){
                is MainListScreenAction.OnItemClick -> { onItemClick(action.todo.id) }
                is MainListScreenAction.OnFABClicked -> { navController.navigate(Route.AddEditTodo()) }
                is MainListScreenAction.OpenManageCategories -> { navController.navigate(Route.Categories.route) }
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoListScreen(
    modifier: Modifier = Modifier,
    state: MainListScreenUiState,
    selectedFilter: List<CategorySummary>,
    hourFormat: Boolean,
    onAction: (MainListScreenAction) -> Unit
){

    val bottomSheetState = rememberModalBottomSheetState()

    Scaffold(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            TitleNavBar(
                title = R.string.my_tasks,
                onGoBack = {},
                showGoBack = false,
                actions = {
                    IconButton(onClick = { onAction(MainListScreenAction.OpenBottomSheet) }) {
                        Icon(
                            imageVector = Icons.Filled.FilterList,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.navigationBarsPadding(),
                onClick = { onAction(MainListScreenAction.OnFABClicked) }
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.End,
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
                hourFormat = hourFormat,
                items = state.sectionedToDos,
                onItemClick = { onAction(MainListScreenAction.OnItemClick(it)) },
                onIsDoneClick = { onAction(MainListScreenAction.OnIsDoneClicked(it))  }
            )
        }
    }
    if(state.showFilterBottomSheet){
        ModalBottomSheet(
            sheetState = bottomSheetState,
            onDismissRequest = { onAction(MainListScreenAction.CloseBottomSheet) }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    text = stringResource(R.string.filter_by_category),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                CategoryFilter(
                    categories = state.categories,
                    selectedCategorySummary = selectedFilter,
                    onCategoryClicked = { onAction(MainListScreenAction.OnCategoryFilterSelected(it)) }
                )
                OutlinedButton(
                    onClick = {
                                onAction(MainListScreenAction.CloseBottomSheet)
                                onAction(MainListScreenAction.OpenManageCategories)
                              },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.manage_categories),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}