package com.example.android4a.injection

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Init the application with the modules and koin
 */
class EsieaApplication : Application() {

    /**
     * Init the application with the modules and koin
     */
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@EsieaApplication)
            modules(presentationModule, domainModule, dataModule)
        }

    }


}