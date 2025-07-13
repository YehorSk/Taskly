package com.yehorsk.taskly.core.utils

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