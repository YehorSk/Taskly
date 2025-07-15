package com.yehorsk.taskly.core.data.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.yehorsk.taskly.core.domain.alarm.NotificationService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AlarmReceiver: BroadcastReceiver(), KoinComponent {

    private val notificationService: NotificationService by inject()

    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("EXTRA_MESSAGE") ?: return
        notificationService.showNotification(message)
        println("Message: $message")
    }

}