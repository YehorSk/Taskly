package com.yehorsk.taskly

import android.Manifest
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.yehorsk.taskly.core.data.alarm.NotificationServiceImpl
import com.yehorsk.taskly.di.databaseModule
import com.yehorsk.taskly.di.repositoryModule
import com.yehorsk.taskly.di.serviceModule
import com.yehorsk.taskly.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            androidLogger()
            modules(databaseModule, repositoryModule, viewModelModule, serviceModule)
        }
        Timber.plant(Timber.DebugTree())
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                NotificationServiceImpl.MAIN_CHANNEL_ID,
                "test",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "Used for testing purposes"

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}