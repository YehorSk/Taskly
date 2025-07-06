package com.yehorsk.taskly.categories.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Color.toLong(): Long = this.value.toLong()
fun Long.toColor(): Color = Color(this)

fun LocalDateTime.formatReadable(): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm", Locale.getDefault())
    return this.format(formatter)
}

fun Boolean.select(
    arrowDropUp: ImageVector,
    arrowDropDown: ImageVector
) : ImageVector {
    return if(this) arrowDropUp
    else arrowDropDown
}
