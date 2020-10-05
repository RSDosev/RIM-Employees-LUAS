package com.rsdosev.rimemployeesluas

import android.app.Application
import com.rsdosev.rimemployeesluas.di.applicationModule
import com.rsdosev.rimemployeesluas.di.dataModule
import com.rsdosev.rimemployeesluas.di.domainModule
import com.rsdosev.rimemployeesluas.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RIMEmployeesLUASApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initDependencyInjectionFramework()
    }

    private fun initDependencyInjectionFramework() {
        startKoin {
            androidLogger()
            androidContext(this@RIMEmployeesLUASApplication)
            modules(
                listOf(
                    applicationModule,
                    dataModule,
                    domainModule,
                    presentationModule
                )
            )
        }
    }
}