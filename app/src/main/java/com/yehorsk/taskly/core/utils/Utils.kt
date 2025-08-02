package com.yehorsk.taskly.core.utils

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

fun formatMonthYMD(input: String): String {
    val parser = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val yearMonth = YearMonth.parse(input, parser)
    val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
    return yearMonth.format(formatter)
}

fun formatMonthYM(input: String): String {
    val parser = DateTimeFormatter.ofPattern("yyyy-MM")
    val yearMonth = YearMonth.parse(input, parser)
    val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
    return yearMonth.format(formatter)
}

val YearMonth.firstDay: LocalDate
    get() = this.atDay(1)

val YearMonth.lastDay: LocalDate
    get() = this.atEndOfMonth()