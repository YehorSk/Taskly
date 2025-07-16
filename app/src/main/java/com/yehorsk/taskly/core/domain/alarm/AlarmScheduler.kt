package com.yehorsk.taskly.core.domain.alarm

import com.yehorsk.taskly.todos.domain.models.ToDo

interface AlarmScheduler {
    fun schedule(item: ToDo)
    fun cancel(item: ToDo)
}