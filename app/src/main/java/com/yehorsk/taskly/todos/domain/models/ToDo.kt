package com.yehorsk.taskly.todos.domain.models

data class ToDo(
    val id: Int,
    val createdAt: Long,
    val title: String,
    val description: String? = null,
    val isDone: Boolean,
    val dueDate: Long,
    val categoryId: Int
)

val sampleToDos = listOf(
    ToDo(
        id = 1,
        createdAt = System.currentTimeMillis(),
        title = "Buy groceries",
        description = "Milk, eggs, bread, fruits",
        isDone = false,
        dueDate = System.currentTimeMillis() + 86400000,
        categoryId = 0
    ),
    ToDo(
        id = 2,
        createdAt = System.currentTimeMillis(),
        title = "Finish project report",
        description = "Due by end of the week",
        isDone = true,
        dueDate = System.currentTimeMillis() + 2 * 86400000,
        categoryId = 0
    ),
    ToDo(
        id = 3,
        createdAt = System.currentTimeMillis(),
        title = "Call plumber",
        description = "Fix leaking kitchen faucet",
        isDone = false,
        dueDate = System.currentTimeMillis() + 3 * 86400000,
        categoryId = 0
    ),
    ToDo(
        id = 4,
        createdAt = System.currentTimeMillis(),
        title = "Schedule dentist appointment",
        description = null,
        isDone = false,
        dueDate = System.currentTimeMillis() + 5 * 86400000,
        categoryId = 0
    ),
    ToDo(
        id = 5,
        createdAt = System.currentTimeMillis(),
        title = "Workout session",
        description = "Leg day at the gym",
        isDone = true,
        dueDate = System.currentTimeMillis() + 1 * 86400000,
        categoryId = 0
    )
)