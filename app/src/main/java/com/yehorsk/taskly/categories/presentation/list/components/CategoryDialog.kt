package com.yehorsk.taskly.categories.presentation.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.yehorsk.taskly.ui.theme.TasklyTheme
import com.yehorsk.taskly.R
import com.yehorsk.taskly.categories.utils.toColor
import com.yehorsk.taskly.core.utils.brightColors

sealed interface CategoryAction{
    data object Insert: CategoryAction
    data object Update: CategoryAction
}

@Composable
fun CategoryDialog(
    categoryTitle: String,
    categoryColor: Long,
    allowSubmit: Boolean,
    action: CategoryAction,
    onTitleChange: (String) -> Unit,
    onColorChange: (Long) -> Unit,
    onButtonClick: () -> Unit,
    onHide: () -> Unit
){
    val (title, button) = when(action){
        CategoryAction.Insert -> Pair(R.string.add_category,R.string.save)
        CategoryAction.Update -> Pair(R.string.update_category,R.string.update)
    }

    Dialog(
        onDismissRequest = { onHide() }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ){
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(1f),
                        text = stringResource(title),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge
                    )
                    IconButton(
                        onClick = {
                            onHide()
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                            )
                        }
                    )
                }
                HorizontalDivider(thickness = 2.dp)
                Text(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp),
                    text = "Title",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                OutlinedTextField(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp),
                    value = categoryTitle,
                    onValueChange = { onTitleChange(it) },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.your_title),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                )
                Text(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp),
                    text = "Choose Color",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    brightColors.forEach { item ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(16.dp))
                                .background(item.toColor())
                                .clickable{
                                    onColorChange(item)
                                }
                        ){
                            if(categoryColor == item){
                                Icon(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    tint = Color.White,
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
                Button(
                    modifier = Modifier
                        .padding(
                            top = 4.dp,
                            bottom = 16.dp,
                            start = 16.dp,
                            end = 16.dp
                        )
                        .fillMaxWidth(),
                    content = {
                        Text(
                            text = stringResource(button),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    onClick = { onButtonClick() },
                    enabled = allowSubmit
                )
            }
        }
    }
}

@Preview
@Composable
fun CategoryDialogPreview(){
    TasklyTheme {
        CategoryDialog(
            categoryTitle = "Lorem Ipsum",
            allowSubmit = false,
            onTitleChange = {},
            onButtonClick = {},
            onHide = {},
            categoryColor = brightColors[0],
            onColorChange = {},
            action = CategoryAction.Insert
        )
    }
}