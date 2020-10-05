package com.rsdosev.rimemployeesluas.base

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rsdosev.domain.CoroutineContextProvider
import com.rsdosev.domain.usecase.GetLuasForecastingUseCase
import com.rsdosev.rimemployeesluas.mock.MockGetLuasForecastingUseCase
import com.rsdosev.rimemployeesluas.di.applicationModule
import com.rsdosev.rimemployeesluas.di.dataModule
import com.rsdosev.rimemployeesluas.di.presentationModule
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
open class BaseUITest {

    /**
     * list of all the Koin modules needed + mocked Domain module
     */
    private val diModules = listOf(
        applicationModule,
        dataModule,
        // Mocked domain module
        module {
            single { CoroutineContextProvider() }

            factory<GetLuasForecastingUseCase> {
                MockGetLuasForecastingUseCase()
            }
        },
        presentationModule
    )

    @Before
    fun setUp() {
        loadKoinModules(diModules)
    }

    @After
    fun tearDown() {
        unloadKoinModules(diModules)
    }
}