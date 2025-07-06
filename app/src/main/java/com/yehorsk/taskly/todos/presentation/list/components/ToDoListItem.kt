package com.yehorsk.taskly.todos.presentation.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Icon
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
import com.yehorsk.taskly.todos.domain.models.ToDo
import com.yehorsk.taskly.ui.theme.TasklyTheme
import java.time.LocalDateTime

@Composable
fun ToDoListItem(
    modifier: Modifier = Modifier,
    todo: ToDo,
    onClick: () -> Unit
){
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable{ onClick() }
            .padding(start = 8.dp, end = 8.dp),
        shape = RoundedCornerShape(18.dp),
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .padding(16.dp),
                tint = Color(0xFF9C27B0),
                imageVector = if(todo.isDone){
                    Icons.Default.CheckCircle
                }else Icons.Default.RadioButtonUnchecked,
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .padding(
                        top = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
                    .weight(1f)
            ) {
                Text(
                    text = todo.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    text = "9:00 am",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(24.dp)
                    .padding(
                        top = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
                    .background(Color(0xFF9C27B0))
            )
        }
    }
}

@Preview
@Composable
fun ToDoListItemPreview(){
    val fakeToDo = ToDo(
        id = 1,
        createdAt = LocalDateTime.now(), // now
        title = "Finish Jetpack Compose homework",
        description = "Review Room integration and AlarmManager usage",
        isDone = false,
        dueDate = LocalDateTime.now(),
        categoryId = 0
    )
    TasklyTheme {
        ToDoListItem(
            todo = fakeToDo,
            onClick = {}
        )
    }
}