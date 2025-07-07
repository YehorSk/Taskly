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
import com.yehorsk.taskly.categories.domain.models.CategoryMain
import com.yehorsk.taskly.ui.theme.TasklyTheme
import java.time.LocalDateTime

@Composable
fun CategoryGrid(
    modifier: Modifier = Modifier,
    items: List<CategoryMain>,
    onClick: (Category) -> Unit,
    onAddNewClick: () -> Unit,
){
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = items,
            key = { it.category.id }
        ) { item ->
            CategoryItem(
                modifier = Modifier
                    .fillMaxWidth(),
                category = item.category,
                count = item.amount,
                onClick = {
                    onClick(item.category)
                }
            )
        }
        item {
            AddNewButton(
                onClick = { onAddNewClick() }
            )
        }
    }
}

//@Preview
//@Composable
//fun CategoryGridPreview(){
//    val categories = listOf(
//        Category(
//            id = 1,
//            title = "Homework",
//            createdAt = LocalDateTime.now(),
//            bgColor = 0xFFE1BEE7, // Purple 100
//            amountOfTasks = 5
//        ),
//        Category(
//            id = 2,
//            title = "Work",
//            createdAt = LocalDateTime.now(),
//            bgColor = 0xFFBBDEFB, // Blue 100
//            amountOfTasks = 10
//        ),
//        Category(
//            id = 3,
//            title = "Shopping",
//            createdAt = LocalDateTime.now(),
//            bgColor = 0xFFC8E6C9, // Green 100
//            amountOfTasks = 8
//        ),
//        Category(
//            id = 4,
//            title = "Fitness",
//            createdAt = LocalDateTime.now(),
//            bgColor = 0xFFFFF9C4, // Yellow 100
//            amountOfTasks = 3
//        ),
//        Category(
//            id = 5,
//            title = "Books to Read",
//            createdAt = LocalDateTime.now(),
//            bgColor = 0xFFD1C4E9, // Purple 100
//            amountOfTasks = 7
//        ),
//        Category(
//            id = 6,
//            title = "Travel Plans",
//            createdAt = LocalDateTime.now(),
//            bgColor = 0xFFFFCCBC, // Orange 100
//            amountOfTasks = 4
//        )
//    )
//    TasklyTheme {
//        CategoryGrid(
//            modifier = Modifier.fillMaxSize(),
//            items = categories,
//            onClick = {},
//            onAddNewClick = {}
//        )
//    }
//}