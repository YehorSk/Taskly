package com.yehorsk.taskly

import android.app.Application
import com.yehorsk.taskly.di.databaseModule
import com.yehorsk.taskly.di.repositoryModule
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
            modules(databaseModule, repositoryModule, viewModelModule)
        }
        Timber.plant(Timber.DebugTree())
    }

}