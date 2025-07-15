package com.yehorsk.taskly.di

import com.yehorsk.taskly.core.data.alarm.AlarmSchedulerImpl
import com.yehorsk.taskly.core.data.alarm.NotificationServiceImpl
import com.yehorsk.taskly.core.domain.alarm.AlarmScheduler
import com.yehorsk.taskly.core.domain.alarm.NotificationService
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val serviceModule = module{

    single<AlarmScheduler> { AlarmSchedulerImpl(androidApplication()) }
    single<NotificationService> { NotificationServiceImpl(androidApplication()) }

}