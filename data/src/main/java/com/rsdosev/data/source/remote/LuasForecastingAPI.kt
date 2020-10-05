package com.rsdosev.data.source.remote

import com.rsdosev.domain.model.Forecast
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_SERVICE_PATH = "https://luasforecasts.rpa.ie/xml/"

interface LuasForecastingAPI {

    @GET("get.ashx?action=forecast&encrypt=false")
    suspend fun getStopForecast(@Query("stop") stopAbr: String): Forecast
}
