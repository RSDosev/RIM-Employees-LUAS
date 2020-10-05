package com.rsdosev.rimemployeesluas.mock

import com.rsdosev.domain.model.Forecast
import com.rsdosev.domain.model.Result
import com.rsdosev.domain.model.TramStop
import com.rsdosev.domain.usecase.GetLuasForecastingUseCase
import kotlinx.coroutines.flow.flowOf

/**
 * Mocked MockGetLuasForecastingUseCase on which we can manipulate the returned forecast on demand
 */
class MockGetLuasForecastingUseCase : GetLuasForecastingUseCase {
    override suspend fun getTramStopForecast(tramStop: TramStop) = flowOf(forecast)

    companion object {
        var forecast: Result<Forecast> = Result.Success(Forecast.dummyForecast1)
    }
}