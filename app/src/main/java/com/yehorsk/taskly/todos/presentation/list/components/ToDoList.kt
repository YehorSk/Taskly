package com.yehorsk.taskly.todos.presentation.list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.taskly.todos.domain.models.SectionedToDo
import com.yehorsk.taskly.todos.domain.models.ToDo
import com.yehorsk.taskly.todos.domain.models.sampleToDos
import com.yehorsk.taskly.ui.theme.TasklyTheme

@Composable
fun ToDoList(
    modifier: Modifier = Modifier,
    items: List<SectionedToDo>,
    onItemClick: (ToDo) -> Unit,
    onIsDoneClick: (ToDo) -> Unit,
){
    LazyColumn(
        modifier = modifier
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items.forEachIndexed { index, (date, todos) ->
            stickyHeader {
                if(index > 0){
                    Spacer(modifier = Modifier.height(16.dp))
                }
                Text(
                    text = date.asString().uppercase(),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            itemsIndexed(
                items = todos,
                key = { _, todo -> todo.id }
            ){ index, todo ->
                ToDoListItem(
                    todo = todo,
                    onClick = { onItemClick(todo) },
                    onDoneClick = { onIsDoneClick(todo) },
                )
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview
@Composable
fun ToDoListPreview(){
    TasklyTheme {
        ToDoList(
            items = sampleToDos,
            onItemClick = {},
            onIsDoneClick = {}
        )
    }
}