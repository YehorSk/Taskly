package com.yehorsk.taskly.notes.presentation.list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yehorsk.taskly.notes.domain.models.Note

@Composable
fun NoteList(
    modifier: Modifier = Modifier,
    items: List<Note>,
    onItemClick: (Note) -> Unit,
){
    LazyColumn(
        modifier = modifier
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(
            items = items,
            key = { _,item -> item.id}
        ){ index, item ->
            NoteListItem(
                note = item,
                onClick = { onItemClick(item) }
            )
        }
    }
}