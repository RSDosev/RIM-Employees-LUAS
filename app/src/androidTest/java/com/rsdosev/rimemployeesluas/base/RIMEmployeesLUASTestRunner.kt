package com.rsdosev.rimemployeesluas.base

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

/**
 * Custom AndroidJUnitRunner class for using our custom test application class
 */
class RIMEmployeesLUASTestRunner : AndroidJUnitRunner() {

    override fun newApplication(
        classLoader: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(classLoader, EmptyDIRIMEmployeesLUASApplication::class.java.name, context)
    }
}