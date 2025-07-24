package com.yehorsk.taskly.todos.presentation.list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.yearMonth
import com.yehorsk.taskly.core.utils.firstDay
import com.yehorsk.taskly.core.utils.lastDay
import com.yehorsk.taskly.ui.theme.TasklyTheme
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CustomDatePicker(
    selectedDates: List<LocalDate>,
    fullCalendar: Boolean,
    onDateChange: (LocalDate) -> Unit,
    onFullCalendarClick: () -> Unit
){
    val currentDate = remember { LocalDate.now() }
    val currentMonth = remember(currentDate) { currentDate.yearMonth }
    val startMonth = remember(currentDate) { currentMonth }
    val endMonth = remember(currentDate) { currentMonth.plusMonths(12) }
    val daysOfWeek = remember { daysOfWeek() }

    val monthState = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first(),
    )

    val weekState = rememberWeekCalendarState(
        startDate = startMonth.firstDay,
        endDate = endMonth.lastDay,
        firstDayOfWeek = daysOfWeek.first(),
        firstVisibleWeekDate = currentDate
    )

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable{
                    onFullCalendarClick()
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = (
                        if (!fullCalendar)
                            weekState.firstVisibleWeek.days[0].date.month
                        else
                            monthState.lastVisibleMonth.yearMonth.month
                        ).getDisplayName(
                    TextStyle.FULL,
                    Locale.getDefault()
                ),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                modifier = Modifier
                    .size(28.dp),
                imageVector = if(fullCalendar) Icons.Default.KeyboardArrowUp
                              else Icons.Default.KeyboardArrowDown,
                contentDescription = null,
            )
        }
        AnimatedVisibility(visible = fullCalendar) {
            HorizontalCalendar(
                state = monthState,
                dayContent = { day ->
                    MonthDayComponent(
                        day = day,
                        isSelected = (day.date in selectedDates) ,
                        onClick = { onDateChange(it) }
                    )
                },
                monthHeader = { month ->
                    val daysOfWeek = month.weekDays.first().map { it.date.dayOfWeek }
                    DaysOfWeekTitle(daysOfWeek = daysOfWeek)
                }
            )
        }
        AnimatedVisibility(visible = !fullCalendar) {
            WeekCalendar(
                state = weekState,
                dayContent = { day ->
                    WeekDayComponent(
                        day = day,
                        isSelected = (day.date in selectedDates) ,
                        onClick = { onDateChange(it) }
                    )
                }
            )
        }
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
            selectedDates = listOf(LocalDate.now(), LocalDate.now().plusDays(1)),
            fullCalendar = false,
            onDateChange = {},
            onFullCalendarClick = {}
        )
    }
}

@Preview(
    backgroundColor = 0xFFFFFFFF,
    showBackground = true
)
@Composable
fun CustomDatePickerPreview2(){
    TasklyTheme {
        CustomDatePicker(
            selectedDates = emptyList(),
            fullCalendar = true,
            onDateChange = {},
            onFullCalendarClick = {}
        )
    }
}