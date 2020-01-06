package com.university.remote

import android.app.Application
import com.university.remote.data.di.dataModule
import com.university.remote.data.di.networkModule
import com.university.remote.domain.di.domainModule
import com.university.remote.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class CarRemoteApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@CarRemoteApplication)
            modules(listOf(domainModule, dataModule, networkModule, presentationModule))
        }
        Timber.plant(Timber.DebugTree())
    }
}