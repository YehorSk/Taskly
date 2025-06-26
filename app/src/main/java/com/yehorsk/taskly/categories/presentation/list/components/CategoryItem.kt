package com.yehorsk.taskly.categories.presentation.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.taskly.categories.domain.models.Category
import com.yehorsk.taskly.categories.utils.toColor
import com.yehorsk.taskly.core.utils.brightColors
import com.yehorsk.taskly.ui.theme.TasklyTheme

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    category: Category,
    onClick: () -> Unit
){
    Surface(
        modifier = modifier
            .clickable{ onClick() }
            .background(category.bgColor.toColor()),
        color = category.bgColor.toColor(),
        shape = RoundedCornerShape(18.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .padding(bottom = 10.dp),
                    text = category.title,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    modifier = Modifier
                        .padding(top = 16.dp),
                    text = "${category.amountOfTasks} Tasks",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White,
                )
            }
        }
    }
}

@Preview
@Composable
fun CategoryItemPreview(){
    TasklyTheme {
        val category = Category(
            id = 1,
            title = "Homework",
            createdAt = System.currentTimeMillis(),
            bgColor = brightColors[0],
            amountOfTasks = 5
        )
        CategoryItem(
            modifier = Modifier
                .fillMaxSize(),
            category = category,
            onClick = {}
        )
    }
}