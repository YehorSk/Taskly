package com.yehorsk.taskly.categories.domain.models

data class Category(
    val id: Int,
    val title: String,
    val createdAt: Long,
    val bgColor: Long,
    val amountOfTasks: Int
)
