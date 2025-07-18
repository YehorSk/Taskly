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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.core.yearMonth
import com.yehorsk.taskly.ui.theme.TasklyTheme
import java.time.LocalDate
import com.yehorsk.taskly.R
import java.time.YearMonth

@Composable
fun CustomDatePicker(
    selectedDates: List<LocalDate>,
    fullCalendar: Boolean,
    onDateChange: (LocalDate) -> Unit,
    onFullCalendarClick: () -> Unit
){
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(50) }
    val endMonth = remember { currentMonth.plusMonths(50) }
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }

    val monthState = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek,
    )

    val currentDate = remember { LocalDate.now() }
    val startDate = remember { currentDate.minusDays(100) }
    val endDate = remember { currentDate.plusDays(100) }

    val weekState = rememberWeekCalendarState(
        startDate = startDate,
        endDate = endDate,
        firstDayOfWeek = firstDayOfWeek
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
                text = stringResource(R.string.calendar),
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
            selectedDates = emptyList(),
            fullCalendar = false,
            onDateChange = {},
            onFullCalendarClick = {}
        )
    }
}