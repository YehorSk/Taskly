package com.yehorsk.taskly.notes.presentation.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.taskly.categories.utils.toColor
import com.yehorsk.taskly.notes.data.database.models.CheckItem
import com.yehorsk.taskly.notes.domain.models.Note
import com.yehorsk.taskly.ui.theme.TasklyTheme
import java.time.LocalDateTime

@Composable
fun CheckListItem(
    modifier: Modifier = Modifier,
    item: CheckItem,
    onClick: (Boolean) -> Unit,
){
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = item.isDone,
            onCheckedChange = {
                onClick(it)
            }
        )
        Text(
            text = item.name
        )
    }
}

@Composable
fun NoteListItem(
    modifier: Modifier = Modifier,
    note: Note,
    onClick: () -> Unit,
){
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable{ onClick() }
            .padding(start = 8.dp, end = 8.dp),
        shadowElevation = 3.dp,
        shape = RoundedCornerShape(18.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(5.dp)
                    .background(note.color.toColor())
            )
            Column(
                modifier = Modifier
                    .padding(
                        top = 8.dp,
                        bottom = 8.dp
                    )
            ){
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                note.checkItems?.forEachIndexed { index, item ->
                    CheckListItem(
                        item = item,
                        onClick = {}
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun NoteListItemPreview(){
    val fakeNote = Note(
        id = 1,
        createdAt = LocalDateTime.now(),
        title = "Grocery Shopping",
        description = "Don't forget these essentials!",
        checkItems = listOf(
            CheckItem(id = "",name = "Milk"),
            CheckItem(id = "",name = "Eggs"),
            CheckItem(id = "",name = "Bread"),
            CheckItem(id = "",name = "Apples"),
            CheckItem(id = "",name = "Cheese", isDone = true)
        ),
        color = 0xFFFFB300
    )
    TasklyTheme {
        NoteListItem(
            note = fakeNote
        ) { }
    }
}