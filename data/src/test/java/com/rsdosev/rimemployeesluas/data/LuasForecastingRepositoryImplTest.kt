package com.rsdosev.rimemployeesluas.data

import com.rsdosev.data.repository.LuasForecastingRepositoryImpl
import com.rsdosev.data.source.local.cache.MemoryCache
import com.rsdosev.data.source.remote.LuasForecastingAPI
import com.rsdosev.domain.model.Forecast.Companion.dummyForecast1
import com.rsdosev.domain.model.Forecast.Companion.dummyForecast2
import com.rsdosev.domain.model.Result
import com.rsdosev.domain.model.Result.Success
import com.rsdosev.domain.model.TramStop.MAR
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

internal class LuasForecastingRepositoryImplTest {

    private val memoryCache = MemoryCache()
    private val service = mockk<LuasForecastingAPI>()
    private var repository = LuasForecastingRepositoryImpl(memoryCache, service)

    @Test
    fun `given success response, when getting tram stop forecast then return Result Success`() {
        runBlocking {

            //given
            coEvery { service.getStopForecast(MAR.abr) } returns dummyForecast1

            // when
            val result = repository.getTramStopForecast(MAR).toList().first()

            // then
            coVerifyOrder {
                service.getStopForecast(MAR.abr)
            }

            assertTrue(result is Success)
            assertEquals(dummyForecast1, (result as Success).data)
        }
    }

    @Test
    fun `given api call fail, when getting tram stop forecast then return Result Error`() {
        runBlocking {
            // given
            coEvery { service.getStopForecast(MAR.abr) } throws IOException()

            // when
            val result = repository.getTramStopForecast(MAR).toList().first()

            // then
            coVerifyOrder {
                service.getStopForecast(MAR.abr)
            }

            assertTrue(result is Result.Error.NetworkError)
        }
    }

    @Test
    fun `given success response, when getting tram stop forecast, then return Result Success, then trying again return cached`() {
        runBlocking {
            // given
            val firstApiResponse = dummyForecast1
            // when
            coEvery { service.getStopForecast(MAR.abr) } returns firstApiResponse
            // then
            val firstApiCallResult = repository.getTramStopForecast(MAR).toList().first()

            // given
            val secondApiResponse = dummyForecast2
            // when
            coEvery { service.getStopForecast(MAR.abr) } returns secondApiResponse
            // then
            val (cachedResult, secondApiCallResult) = (repository.getTramStopForecast(MAR).toList())

            // then
            coVerify(exactly = 2) {
                service.getStopForecast(MAR.abr)
            }

            assertTrue(firstApiCallResult is Success)
            assertTrue((firstApiCallResult as? Success)?.data == firstApiResponse)
            assertTrue(cachedResult is Success)
            assertTrue((cachedResult as? Success)?.data == firstApiResponse)
            assertTrue(secondApiCallResult is Success)
            assertTrue((secondApiCallResult as? Success)?.data != (firstApiCallResult as? Success)?.data)
        }
    }
}