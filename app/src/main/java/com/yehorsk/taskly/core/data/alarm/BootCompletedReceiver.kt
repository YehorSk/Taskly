package com.yehorsk.taskly.core.data.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.yehorsk.taskly.todos.domain.repository.ToDoRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BootCompletedReceiver: BroadcastReceiver(), KoinComponent {

    private val toDoRepository: ToDoRepository by inject()

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == Intent.ACTION_BOOT_COMPLETED){
            println("Booted Up")
        }
    }

}