package com.yehorsk.taskly.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import com.kizitonwose.calendar.compose.CalendarLayoutInfo
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.core.CalendarMonth
import kotlinx.coroutines.flow.filterNotNull
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

val brightColors  = listOf(
    0xFFEF5350, // Tomato Red
    0xFFFFB300, // Vivid Amber
    0xFFEC407A, // Rose Pink
    0xFF9575CD, // Medium Purple
    0xFF42A5F5  // Sky Blue
)

sealed interface AddEditAction{
    data object EDIT: AddEditAction
    data object ADD: AddEditAction
}

fun formatMonth(input: String): String {
    val parser = DateTimeFormatter.ofPattern("yyyy-MM")
    val yearMonth = YearMonth.parse(input, parser)
    val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
    return yearMonth.format(formatter)
}

val YearMonth.firstDay: LocalDate
    get() = this.atDay(1)

val YearMonth.lastDay: LocalDate
    get() = this.atEndOfMonth()