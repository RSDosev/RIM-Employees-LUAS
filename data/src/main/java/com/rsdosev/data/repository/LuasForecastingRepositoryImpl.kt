package com.rsdosev.data.repository

import com.rsdosev.data.source.remote.LuasForecastingAPI
import com.rsdosev.domain.model.Result
import com.rsdosev.data.source.remote.apiCall
import com.rsdosev.data.source.local.cache.MemoryCache
import com.rsdosev.domain.model.Forecast
import com.rsdosev.domain.model.TramStop
import com.rsdosev.domain.repository.LuasForecastingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


const val TRAM_STOPS_FORECAST_LIST_CACHE_KEY = "TRAM_STOPS_FORECAST_LIST_CACHE_KEY"

class LuasForecastingRepositoryImpl(
    private val memoryCache: MemoryCache,
    private val api: LuasForecastingAPI
) : LuasForecastingRepository {

    /**
     * In-memory cache of tram stops forecast
     */
    private var cache: MutableMap<TramStop, Forecast>
        get() = memoryCache.load<MutableMap<TramStop, Forecast>>(TRAM_STOPS_FORECAST_LIST_CACHE_KEY) ?: run {
            mutableMapOf<TramStop, Forecast>().apply {
                cache = this
            }
        }
        set(value) = memoryCache.save(TRAM_STOPS_FORECAST_LIST_CACHE_KEY, value)

    /**
     * Fetches the forecast for the given Tram stop from LUAS Forecasting API, saved the result in in-memory cache and returns the result.
     * Any subsequent method calls will first emit any cached data if present and then return an API response if successful.
     * API call failures are returned only if the cache is empty.
     *
     * @return the result of getting the tram stop forecast
     */
    override suspend fun getTramStopForecast(tramStop: TramStop): Flow<Result<Forecast>> = flow {
        cache[tramStop]?.let {
            emit(Result.Success(it))
        }

        val apiResult = apiCall { api.getStopForecast(tramStop.abr) }

        apiResult.toString()
        if (apiResult is Result.Success) {
            cache[tramStop] = apiResult.data
            emit(
                Result.Success(
                    apiResult.data
                )
            )
        } else if (cache[tramStop] == null) {
            emit(apiResult)
        }
    }
}
