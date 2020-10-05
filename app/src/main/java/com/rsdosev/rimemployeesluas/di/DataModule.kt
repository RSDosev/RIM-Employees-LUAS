package com.rsdosev.rimemployeesluas.di

import com.rsdosev.domain.repository.LuasForecastingRepository
import com.rsdosev.data.repository.LuasForecastingRepositoryImpl
import com.rsdosev.data.source.local.cache.DEFAULT_CACHE_SIZE
import com.rsdosev.data.source.local.cache.MemoryCache
import org.koin.dsl.module

val dataModule = module {
    single { MemoryCache(DEFAULT_CACHE_SIZE) }
    factory<LuasForecastingRepository> { LuasForecastingRepositoryImpl(get(), get()) }
}