package com.yehorsk.taskly.categories.presentation.list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.taskly.categories.domain.models.Category
import com.yehorsk.taskly.ui.theme.TasklyTheme

@Composable
fun CategoryGrid(
    modifier: Modifier = Modifier,
    items: List<Category>,
    onClick: (Category) -> Unit
){
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = items,
            key = { it.id }
        ) { item ->
            CategoryItem(
                modifier = Modifier
                    .fillMaxWidth(),
                category = item,
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
fun CategoryGridPreview(){
    val categories = listOf(
        Category(
            id = 1,
            title = "Homework",
            createdAt = System.currentTimeMillis(),
            bgColor = 0xFFE1BEE7, // Purple 100
            amountOfTasks = 5
        ),
        Category(
            id = 2,
            title = "Work",
            createdAt = System.currentTimeMillis() - 86_400_000, // 1 day ago
            bgColor = 0xFFBBDEFB, // Blue 100
            amountOfTasks = 10
        ),
        Category(
            id = 3,
            title = "Shopping",
            createdAt = System.currentTimeMillis() - 172_800_000, // 2 days ago
            bgColor = 0xFFC8E6C9, // Green 100
            amountOfTasks = 8
        ),
        Category(
            id = 4,
            title = "Fitness",
            createdAt = System.currentTimeMillis() - 259_200_000, // 3 days ago
            bgColor = 0xFFFFF9C4, // Yellow 100
            amountOfTasks = 3
        ),
        Category(
            id = 5,
            title = "Books to Read",
            createdAt = System.currentTimeMillis() - 345_600_000, // 4 days ago
            bgColor = 0xFFD1C4E9, // Purple 100
            amountOfTasks = 7
        ),
        Category(
            id = 6,
            title = "Travel Plans",
            createdAt = System.currentTimeMillis() - 432_000_000, // 5 days ago
            bgColor = 0xFFFFCCBC, // Orange 100
            amountOfTasks = 4
        )
    )
    TasklyTheme {
        CategoryGrid(
            modifier = Modifier.fillMaxSize(),
            items = categories,
            onClick = {}
        )
    }
}