package com.yehorsk.taskly.todos.presentation.list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.taskly.todos.domain.models.ToDo
import com.yehorsk.taskly.todos.domain.models.sampleToDos
import com.yehorsk.taskly.ui.theme.TasklyTheme

@Composable
fun ToDoList(
    modifier: Modifier = Modifier,
    items: List<ToDo>,
    onItemClick: (ToDo) -> Unit
){
    LazyColumn(
        modifier = modifier
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items = items, key = { it.id }){ todo ->
            ToDoListItem(
                todo = todo,
                onClick = { onItemClick(todo) }
            )
        }
    }

}

@Preview
@Composable
fun ToDoListPreview(){
    TasklyTheme {
        ToDoList(
            items = sampleToDos,
            onItemClick = {}
        )
    }
}