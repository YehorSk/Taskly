package com.yehorsk.taskly.categories.presentation.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.taskly.categories.presentation.list.components.CategoryGrid
import org.koin.compose.viewmodel.koinViewModel
import com.yehorsk.taskly.R
import com.yehorsk.taskly.categories.presentation.list.components.CategoryAction
import com.yehorsk.taskly.categories.presentation.list.components.CategoryDialog

@Composable
fun CategoryScreenRoot(
    viewModel: CategoryScreenViewModel = koinViewModel()
){

    val state by viewModel.state.collectAsStateWithLifecycle()

    CategoryScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun CategoryScreen(
    state: CategoryScreenUiState,
    onAction: (CategoryScreenAction) -> Unit
){
    Scaffold { contentPadding ->
        if(state.showAddCategoryDialog){
            CategoryDialog(
                categoryTitle = state.title,
                categoryColor = state.color,
                onHide = { onAction(CategoryScreenAction.HideCategoryDialog) },
                onButtonClick = { onAction(CategoryScreenAction.OnCategorySave) },
                onTitleChange = { onAction(CategoryScreenAction.OnCategoryTitleChange(it)) },
                onColorChange = { onAction(CategoryScreenAction.OnCategoryColorChange(it)) },
                action = CategoryAction.Insert,
                allowSubmit = true
            )
        }
        if(state.items.isEmpty()){
            Box(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(R.drawable.empty_list),
                    contentDescription = ""
                )
            }
        }else{
            CategoryGrid(
                modifier = Modifier.padding(contentPadding),
                items = state.items,
                onClick = { onAction(CategoryScreenAction.OnCategoryClick(it)) },
                onAddNewClick = { onAction(CategoryScreenAction.ShowCategoryDialog) }
            )
        }
    }
}