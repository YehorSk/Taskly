package com.yehorsk.taskly.core.data.alarm

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import com.yehorsk.taskly.core.domain.alarm.AlarmScheduler
import com.yehorsk.taskly.todos.domain.models.ToDo
import java.time.ZoneId

class AlarmSchedulerImpl(
    private val context: Context
) : AlarmScheduler{

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    override fun schedule(item: ToDo) {
        val dueDateTime = item.dueDate ?: return
        val intent = Intent(
            context, AlarmReceiver::class.java
        ).apply {
            putExtra("EXTRA_MESSAGE", item.title)
        }
        val triggerAtMillis = dueDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            PendingIntent.getBroadcast(
                context,
                item.id.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancel(item: ToDo) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                item.id.hashCode(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

}