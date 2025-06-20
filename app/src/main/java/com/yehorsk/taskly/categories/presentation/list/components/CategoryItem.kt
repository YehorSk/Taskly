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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.taskly.categories.domain.models.Category
import com.yehorsk.taskly.categories.utils.toColor
import com.yehorsk.taskly.ui.theme.TasklyTheme

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    category: Category,
    onClick: () -> Unit
){
    val colorStops = arrayOf(
        0.9f to category.bgColor.toColor(),
        1f to Color.White
    )
//    val brush = Brush.verticalGradient(listOf(category.bgColor.toColor(), Color.White))
    val brush = Brush.verticalGradient(colorStops = colorStops)
    Surface(
        modifier = modifier
            .clickable{ onClick() },
        color = Color.Transparent,
        shape = RoundedCornerShape(18.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = brush)
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
                        .padding(bottom = 5.dp),
                    text = category.title.take(2).uppercase(),
                    style = MaterialTheme.typography.headlineMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier
                        .padding(bottom = 10.dp),
                    text = category.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "${category.amountOfTasks} Todo",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black,
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
            bgColor = 0xFFE1BEE7,
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