package com.yehorsk.taskly.todos.presentation.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.core.WeekDay
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale


@Composable
fun WeekDayComponent(
    day: WeekDay,
    isSelected: Boolean = false,
    onClick: (LocalDate) -> Unit
) {

    val isPastDate = day.date.isBefore(LocalDate.now())
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    Box(
        modifier = Modifier
            .width(screenWidth / 7)
            .padding(2.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color = when{
                isSelected -> MaterialTheme.colorScheme.primaryContainer
                isPastDate -> Color.LightGray
                else -> Color.White
            })
            .border(
                width = 2.dp,
                color = when{
                    isSelected -> Color.Transparent
                    isPastDate -> Color.Transparent
                    else -> Color.LightGray
                },
                shape = RoundedCornerShape(16.dp))
            .clickable{ onClick(day.date) },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(bottom = 10.dp, top = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = day.date.dayOfWeek.getDisplayName(TextStyle.SHORT_STANDALONE, Locale.getDefault()),
            )
            Text(
                text = day.date.dayOfMonth.toString(),
            )
        }
    }
}