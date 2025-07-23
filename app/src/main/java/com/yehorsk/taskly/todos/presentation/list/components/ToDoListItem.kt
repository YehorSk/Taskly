package com.yehorsk.taskly.todos.presentation.list.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.taskly.categories.utils.getTime
import com.yehorsk.taskly.todos.domain.models.ToDo
import com.yehorsk.taskly.ui.theme.TasklyTheme
import com.yehorsk.taskly.categories.utils.toColor
import java.time.LocalDateTime

@Composable
fun ToDoListItem(
    modifier: Modifier = Modifier,
    todo: ToDo,
    onClick: () -> Unit,
    onDoneClick: () -> Unit
){
    val isOverDue = if (todo.dueDate!!.isBefore(LocalDateTime.now()) && !todo.isDone) Color.Red else Color.Transparent
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable{ onClick() }
            .padding(start = 8.dp, end = 8.dp),
        shadowElevation = 3.dp,
        border = BorderStroke(2.dp, isOverDue),
        shape = RoundedCornerShape(18.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(62.dp)
                    .clickable{
                        onDoneClick()
                    },
                contentAlignment = Alignment.Center
            ){
                Icon(
                    modifier = Modifier
                        .size(32.dp),
                    tint = todo.bgColor!!.toColor(),
                    imageVector = if(todo.isDone){
                        Icons.Default.CheckCircle
                    }else Icons.Default.RadioButtonUnchecked,
                    contentDescription = null
                )
            }
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
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = MaterialTheme.typography.headlineSmall.color.copy(
                            alpha = if (todo.isDone) 0.5f else 1f
                        )
                    ),
                    textDecoration = if(todo.isDone) TextDecoration.LineThrough else null,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    textDecoration = if(todo.isDone) TextDecoration.LineThrough else null,
                    text = todo.dueDate.getTime(),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.typography.headlineSmall.color.copy(
                            alpha = if (todo.isDone) 0.5f else 1f
                        )
                    )

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
                    .background(todo.bgColor!!.toColor())
            )
        }
    }
}

@Preview(
    backgroundColor = 0xFFFFFFFF,
    showBackground = true
)
@Composable
fun ToDoListItemPreview(){
    val fakeToDo = ToDo(
        id = 1,
        createdAt = LocalDateTime.now(),
        title = "Finish Jetpack Compose homework",
        description = "Review Room integration and AlarmManager usage",
        isDone = false,
        dueDate = LocalDateTime.now().plusDays(1),
        categoryId = 0,
        bgColor = 0xFFFFB300
    )
    TasklyTheme {
        Column(
            modifier = Modifier
                .height(200.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ToDoListItem(
                todo = fakeToDo,
                onClick = {},
                onDoneClick = {}
            )
        }
    }
}

@Preview(
    backgroundColor = 0xFFFFFFFF,
    showBackground = true
)
@Composable
fun ToDoListItemIsDonePreview(){
    val fakeToDo = ToDo(
        id = 1,
        createdAt = LocalDateTime.now(),
        title = "Finish Jetpack Compose homework",
        description = "Review Room integration and AlarmManager usage",
        isDone = true,
        dueDate = LocalDateTime.now().plusDays(1),
        categoryId = 1,
        bgColor = 0xFFFFB300
    )
    TasklyTheme {
        Column(
            modifier = Modifier
                .height(200.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ToDoListItem(
                todo = fakeToDo,
                onClick = {},
                onDoneClick = {}
            )
        }
    }
}