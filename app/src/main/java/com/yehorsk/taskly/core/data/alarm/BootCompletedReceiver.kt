package com.yehorsk.taskly.core.data.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.yehorsk.taskly.todos.domain.repository.ToDoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class BootCompletedReceiver: BroadcastReceiver(), KoinComponent {

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == Intent.ACTION_BOOT_COMPLETED){
            println("Booted Up")
            val toDoRepository: ToDoRepository = getKoin().get()
            CoroutineScope(Dispatchers.Default).launch {
                toDoRepository.rescheduleAlarms()
            }
        }
    }

}