package com.yehorsk.taskly.notes.domain.models

import com.yehorsk.taskly.notes.data.database.models.CheckList
import java.time.LocalDateTime

data class Note(
    val id: Int,
    val createdAt: LocalDateTime,
    val title: String,
    val description: String? = null,
    val checkList: CheckList? = null
)
