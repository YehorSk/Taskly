package com.yehorsk.taskly.categories.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.taskly.R
import com.yehorsk.taskly.categories.presentation.list.components.CategoryGrid
import org.koin.compose.viewmodel.koinViewModel
import com.yehorsk.taskly.categories.presentation.list.components.CategoryDialog
import com.yehorsk.taskly.core.presentation.components.TitleNavBar
import com.yehorsk.taskly.core.utils.AddEditAction

@Composable
fun CategoryScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: CategoryScreenViewModel = koinViewModel()
){

    val state by viewModel.state.collectAsStateWithLifecycle()

    CategoryScreen(
        modifier = modifier,
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    state: CategoryScreenUiState,
    onAction: (CategoryScreenAction) -> Unit
){
    if(state.showAddCategoryDialog){
        CategoryDialog(
            categoryTitle = state.title,
            categoryColor = state.color,
            onHide = { onAction(CategoryScreenAction.HideCategoryDialog) },
            onButtonClick = {
                when(state.action){
                    AddEditAction.ADD -> onAction(CategoryScreenAction.OnCategorySave)
                    AddEditAction.EDIT -> onAction(CategoryScreenAction.OnCategoryUpdate)
                }
            },
            onDeleteClick = { onAction(CategoryScreenAction.OnCategoryDelete) },
            onTitleChange = { onAction(CategoryScreenAction.OnCategoryTitleChange(it)) },
            onColorChange = { onAction(CategoryScreenAction.OnCategoryColorChange(it)) },
            action = state.action,
            allowSubmit = true
        )
    }
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            TitleNavBar(
                title = R.string.categories,
                onGoBack = {},
                showGoBack = false
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ){
            if(state.isLoading){
                CircularProgressIndicator()
            }else{
                CategoryGrid(
                    modifier = Modifier.fillMaxSize(),
                    items = state.itemsMain,
                    onClick = { onAction(CategoryScreenAction.OnCategoryClick(it)) },
                    onAddNewClick = { onAction(CategoryScreenAction.ShowCategoryDialog) }
                )
            }
        }
    }
}