package com.example.basecomposeproject

import android.app.Application
import com.example.basecomposeproject.di.appModule
import com.example.basecomposeproject.di.configModule
import com.example.core.di.coreModule
import com.example.data.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class ExampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())


        startKoin {
            androidContext(this@ExampleApplication)
            modules(
                configModule,
                coreModule,
                dataModule,
                appModule,
            )
        }
    }
}