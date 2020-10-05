package com.rsdosev.rimemployeesluas.di

import com.rsdosev.domain.interactors.GetLuasForecastingInteractor
import com.rsdosev.domain.usecase.GetLuasForecastingUseCase
import com.rsdosev.domain.CoroutineContextProvider
import org.koin.dsl.module

val domainModule = module {
    single { CoroutineContextProvider() }

    factory<GetLuasForecastingUseCase> {
        GetLuasForecastingInteractor(
            get(),
            get()
        )
    }
}