package com.rsdosev.rimemployeesluas.base


import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

/**
 * Custom test application class used for providing custom mocked Koin modules on demand
 */
class EmptyDIRIMEmployeesLUASApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@EmptyDIRIMEmployeesLUASApplication)
        }
    }

    override fun onTerminate() {
        stopKoin()
        super.onTerminate()
    }
}