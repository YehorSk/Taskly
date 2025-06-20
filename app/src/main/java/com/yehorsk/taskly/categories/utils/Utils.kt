package com.yehorsk.taskly.categories.utils

import androidx.compose.ui.graphics.Color

fun Color.toLong(): Long = this.value.toLong()
fun Long.toColor(): Color = Color(this)