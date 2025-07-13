package com.yehorsk.taskly.todos.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yehorsk.taskly.R
import com.yehorsk.taskly.core.presentation.components.TitleNavBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainListScreen(
    modifier: Modifier = Modifier,
    viewModel: MainListScreenViewModel = koinViewModel(),
    onItemClick: (Int) -> Unit
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        TitleNavBar(
            title = R.string.my_tasks,
            onGoBack = {},
            showGoBack = false
        )
        ListScreenRoot(
            modifier = Modifier.fillMaxSize(),
            viewModel = viewModel,
            onItemClick = { onItemClick(it.id) }
        )
    }
}