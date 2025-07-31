package com.yehorsk.taskly.settings.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SwitchListItem(
    checked: Boolean,
    iconChecked: ImageVector,
    iconUnchecked: ImageVector,
    onSwitched: (Boolean) -> Unit
){
    Row(
        modifier = Modifier
            .padding(
                bottom = 16.dp,
                start = 16.dp,
                end = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .padding(end = 16.dp),
            text = "Theme: ",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Switch(
            checked = checked,
            onCheckedChange = { onSwitched(it) },
            thumbContent = if(checked){
                {
                    Icon(
                        imageVector = iconChecked,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                }
            } else {
                {
                    Icon(
                        imageVector = iconUnchecked,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                }
            }
        )
    }
}