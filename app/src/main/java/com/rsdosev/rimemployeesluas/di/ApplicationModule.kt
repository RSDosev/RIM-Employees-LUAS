package com.rsdosev.rimemployeesluas.di

import com.rsdosev.data.source.remote.BASE_SERVICE_PATH
import com.rsdosev.data.source.remote.LuasForecastingAPI
import com.rsdosev.data.source.remote.client.RetrofitClient
import org.koin.dsl.module

val applicationModule = module {
    single<LuasForecastingAPI> {
        RetrofitClient(BASE_SERVICE_PATH).luasForecastingAPIService
    }
}
