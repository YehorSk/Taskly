package com.yehorsk.taskly.todos.presentation.list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.yehorsk.taskly.ui.theme.TasklyTheme
import java.time.LocalDate

@Composable
fun CustomDatePicker(
    selectedDate: LocalDate,
    onDateChange: (LocalDate) -> Unit
){
    val startDate = remember { selectedDate.minusDays(100) }
    val endDate = remember { selectedDate.plusDays(100) }
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }
    val weekState = rememberWeekCalendarState(
        startDate = startDate,
        endDate = endDate,
        firstDayOfWeek = firstDayOfWeek
    )

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${selectedDate.dayOfMonth} ${selectedDate.month}",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                modifier = Modifier
                    .size(28.dp),
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
            )
        }
        WeekCalendar(
            state = weekState,
            dayContent = { day ->
                WeekDayComponent(
                    day = day,
                    isSelected = selectedDate == day.date,
                    onClick = { onDateChange(it) }
                )
            }
        )
    }
}

@Preview(
    backgroundColor = 0xFFFFFFFF,
    showBackground = true
)
@Composable
fun CustomDatePickerPreview(){
    TasklyTheme {
        CustomDatePicker(
            selectedDate = LocalDate.now()
        ) { }
    }
}