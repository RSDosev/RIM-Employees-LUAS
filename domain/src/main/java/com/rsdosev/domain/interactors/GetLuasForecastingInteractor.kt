package com.rsdosev.domain.interactors

import com.rsdosev.domain.CoroutineContextProvider
import com.rsdosev.domain.model.Forecast
import com.rsdosev.domain.model.Result
import com.rsdosev.domain.model.TramStop
import com.rsdosev.domain.repository.LuasForecastingRepository
import com.rsdosev.domain.usecase.GetLuasForecastingUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn


class GetLuasForecastingInteractor(
    private val luasForecastingRepository: LuasForecastingRepository,
    private val coroutineContextProvider: CoroutineContextProvider
) : GetLuasForecastingUseCase {

    override suspend fun getTramStopForecast(tramStop: TramStop) =
        luasForecastingRepository.getTramStopForecast(tramStop).flowOn(coroutineContextProvider.IO)
}