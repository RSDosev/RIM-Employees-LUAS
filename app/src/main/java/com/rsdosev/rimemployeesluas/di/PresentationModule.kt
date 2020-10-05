package com.rsdosev.rimemployeesluas.di

import com.rsdosev.rimemployeesluas.tramstopforecast.TramStopForecastViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { TramStopForecastViewModel(get()) }
}