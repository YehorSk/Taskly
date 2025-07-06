package com.yehorsk.taskly.categories.domain.models

import java.time.LocalDateTime


data class Category(
    val id: Int = 0,
    val title: String,
    val createdAt: LocalDateTime,
    val bgColor: Long,
    val amountOfTasks: Int = 0
)
