package com.rsdosev.domain.repository

import com.rsdosev.domain.model.Forecast
import com.rsdosev.domain.model.Result
import com.rsdosev.domain.model.TramStop
import kotlinx.coroutines.flow.Flow

interface LuasForecastingRepository {

    /**
     * Gets the Luas forecasting for particular stop
     *
     * @return the stop forecast
     */
    suspend fun getTramStopForecast(tramStop: TramStop): Flow<Result<Forecast>>
}
