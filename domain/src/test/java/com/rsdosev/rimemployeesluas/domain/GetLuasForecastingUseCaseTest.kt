package com.rsdosev.rimemployeesluas.domain

import com.rsdosev.rimemployeesluas.TestCoroutineContextProvider
import com.rsdosev.domain.model.Result
import com.rsdosev.domain.model.Result.Success
import com.rsdosev.domain.repository.LuasForecastingRepository
import com.rsdosev.domain.interactors.GetLuasForecastingInteractor
import com.rsdosev.domain.model.Forecast
import com.rsdosev.domain.model.Forecast.Companion.dummyForecast1
import com.rsdosev.domain.model.Forecast.Companion.dummyForecast2
import com.rsdosev.domain.model.TramStop
import com.rsdosev.domain.model.TramStop.MAR
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

internal class GetLuasForecastingUseCaseTest {

    private val repository = mockk<LuasForecastingRepository>()
    private val interactor =
        GetLuasForecastingInteractor(
            repository,
            TestCoroutineContextProvider()
        )

    private val ERROR_MSG = "errorMsg"

    @Test
    fun `given success when getting tram stop forecast then return Result Success`() {
        runBlocking {
            // given
            coEvery { repository.getTramStopForecast(MAR) } returns flowOf(Success(dummyForecast1))

            // when
            val result = interactor.getTramStopForecast(MAR).toList().first()

            // then
            coVerifyOrder {
                repository.getTramStopForecast(MAR)
            }

            assertTrue(result is Success)
            assertTrue((result as? Success)?.data == dummyForecast1)
        }
    }

    @Test
    fun `given error when getting tram stop forecast then return Result Error`() {
        runBlocking {
            // given
            coEvery { repository.getTramStopForecast(MAR) } returns flowOf(
                Result.Error.BasicError(
                    errorMsg = ERROR_MSG
                )
            )

            // when
            val result = interactor.getTramStopForecast(MAR).toList().first()

            // then
            coVerifyOrder {
                repository.getTramStopForecast(MAR)
            }

            assertTrue(result is Result.Error)
            assertEquals(ERROR_MSG, (result as? Result.Error.BasicError)?.errorMsg)
        }
    }

    @Test
    fun `given network error when getting tram stop forecast then return Result NetworkError`() {
        runBlocking {
            // given
            coEvery { repository.getTramStopForecast(MAR) } returns flowOf(Result.Error.NetworkError)

            // when
            val result = interactor.getTramStopForecast(MAR).toList().first()

            // then
            coVerifyOrder {
                repository.getTramStopForecast(MAR)
            }

            assertTrue(result is Result.Error.NetworkError)
        }
    }

    @Test
    fun `given other errors when getting tram stop forecast then return Result GenericError`() {
        runBlocking {
            // given
            coEvery { repository.getTramStopForecast(MAR) } returns flowOf(Result.Error.ApiCallError())

            // when
            val result = interactor.getTramStopForecast(MAR).toList().first()

            // then
            coVerifyOrder {
                repository.getTramStopForecast(MAR)
            }

            assertTrue(result is Result.Error.ApiCallError)
        }
    }
}