package com.yehorsk.taskly.categories.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Color.toLong(): Long = this.value.toLong()
fun Long.toColor(): Color = Color(this)

fun LocalDateTime.formatReadable(hourFormat: Boolean): String {
    val formatter = if(hourFormat){
        DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm", Locale.getDefault())
    }else{
        DateTimeFormatter.ofPattern("dd MMM yyyy, HH a", Locale.getDefault())
    }
    return this.format(formatter)
}

fun LocalDateTime.getTime(hourFormat: Boolean): String {
    val formatter = if(hourFormat){
        DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())
    }else{
        DateTimeFormatter.ofPattern("HH a", Locale.getDefault())
    }
    return this.format(formatter)
}

fun Boolean.select(
    arrowDropUp: ImageVector,
    arrowDropDown: ImageVector
) : ImageVector {
    return if(this) arrowDropUp
    else arrowDropDown
}
