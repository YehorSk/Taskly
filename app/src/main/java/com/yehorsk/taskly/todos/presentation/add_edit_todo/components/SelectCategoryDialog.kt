package com.yehorsk.taskly.todos.presentation.add_edit_todo.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.yehorsk.taskly.todos.domain.models.CategorySummary

@Composable
fun SelectCategoryDialog(
    categories: List<CategorySummary>,
    selectedIndex: Int,
    label: String? = null,
    onSelect: (CategorySummary) -> Unit,
    onDismissRequest: () -> Unit
){
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp)
        ) {
            val listState = rememberLazyListState()
            if (selectedIndex > -1) {
                LaunchedEffect("ScrollToSelected") {
                    listState.scrollToItem(index = selectedIndex)
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                state = listState
            ) {
                if (label != null) {
                    item {
                        LargeDropdownMenuItem(
                            text = label,
                            selected = false,
                            enabled = false,
                            onClick = { },
                        )
                    }
                }
                itemsIndexed(items = categories){ index, item ->
                    val selectedItem = index == selectedIndex
                    LargeDropdownMenuItem(
                        text = item.title,
                        selected = selectedItem,
                        enabled = false,
                        onClick = { },
                    )
                    if (index < categories.lastIndex) {
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun LargeDropdownMenuItem(
    text: String,
    selected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    val contentColor = when {
        !enabled -> MaterialTheme.colorScheme.onSurface
        selected -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.onSurface
    }

    Box(modifier = Modifier
        .clickable(enabled) { onClick() }
        .fillMaxWidth()
        .padding(16.dp)) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
        )
    }
}