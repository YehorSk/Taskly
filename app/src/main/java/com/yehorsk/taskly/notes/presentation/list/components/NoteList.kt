package com.yehorsk.taskly.notes.presentation.list.components

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
import androidx.compose.ui.unit.dp
import com.yehorsk.taskly.notes.data.database.models.CheckItem
import com.yehorsk.taskly.notes.domain.models.Note
import com.yehorsk.taskly.notes.domain.models.SectionedNotes

@Composable
fun NoteList(
    modifier: Modifier = Modifier,
    items: List<SectionedNotes>,
    onItemClick: (Note) -> Unit,
    onCheckItemClick: (Note, CheckItem) -> Unit
){
    LazyColumn(
        modifier = modifier
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items.forEachIndexed { index, (date, notes) ->
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
                items = notes,
                key = { _,item -> item.id}
            ){ index, item ->
                NoteListItem(
                    note = item,
                    onClick = { onItemClick(item) },
                    onCheckItemClick = { note, item ->  onCheckItemClick(note, item) }
                )
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}