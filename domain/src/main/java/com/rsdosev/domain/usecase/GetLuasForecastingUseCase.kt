package com.rsdosev.domain.usecase

import com.rsdosev.domain.model.Forecast
import kotlinx.coroutines.flow.Flow
import com.rsdosev.domain.model.Result
import com.rsdosev.domain.model.TramStop

interface GetLuasForecastingUseCase {
    /**
     * Gets the Luas forecasting for particular stop
     *
     * @return the stop forecast
     */
    suspend fun getTramStopForecast(tramStop: TramStop): Flow<Result<Forecast>>
}