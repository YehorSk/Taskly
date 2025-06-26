package com.yehorsk.taskly.categories.presentation.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.taskly.ui.theme.TasklyTheme

@Composable
fun AddNewButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    Surface(
        modifier = modifier
            .size(100.dp)
            .clickable{
                onClick()
            },
        color = Color(0xFF738CFC),
        shape = RoundedCornerShape(18.dp),
    ) {
        Icon(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            tint = Color.White,
            imageVector = Icons.Default.Add,
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun AddNewButtonPreview(){
    TasklyTheme {
        AddNewButton {

        }
    }
}