package com.yehorsk.taskly.core.data.alarm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.yehorsk.taskly.MainActivity
import com.yehorsk.taskly.R
import com.yehorsk.taskly.core.domain.alarm.NotificationService

class NotificationServiceImpl(
    private val context: Context
): NotificationService {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override fun showNotification(message: String) {
        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )
        val notification = NotificationCompat.Builder(context,MAIN_CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_waving_hand_24)
            .setContentTitle("Taskly")
            .setContentText("Message: $message")
            .setContentIntent(activityPendingIntent)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(
            1,
            notification
        )
    }

    companion object{
        const val MAIN_CHANNEL_ID = "main_channel"
    }

}