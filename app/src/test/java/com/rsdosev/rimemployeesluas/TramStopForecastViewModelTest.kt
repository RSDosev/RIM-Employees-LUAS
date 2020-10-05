package com.rsdosev.rimemployeesluas

import androidx.lifecycle.Observer
import com.rsdosev.domain.model.Forecast.Companion.dummyForecast1
import com.rsdosev.domain.model.Forecast.Companion.dummyForecast2
import com.rsdosev.domain.model.Result
import com.rsdosev.domain.model.Result.Success
import com.rsdosev.domain.model.TramStop.MAR
import com.rsdosev.domain.model.TramStop.STI
import com.rsdosev.domain.usecase.GetLuasForecastingUseCase
import com.rsdosev.rimemployeesluas.tramstopforecast.TramStopForecastViewModel
import com.rsdosev.rimemployeesluas.utils.ViewState
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.anyOf
import org.junit.Assert
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test


class TramStopForecastViewModelTest : BaseViewModelTest() {

    private val useCase = mockk<GetLuasForecastingUseCase>()
    private val observer = mockk<Observer<ViewState>>(relaxUnitFun = true)
    private val viewModel = TramStopForecastViewModel(useCase)

    @Before
    override fun setup() {
        super.setup()
        viewModel.viewState.observeForever(observer)
    }

    @Test
    fun `given success, when getting tram stop forecast, then return ViewState Successful`() {

        //given
        coEvery { useCase.getTramStopForecast(MAR) } returns flowOf(Success(dummyForecast1))
        coEvery { useCase.getTramStopForecast(STI) } returns flowOf(Success(dummyForecast2))

        //when
        viewModel.getTramForecast()

        //then
        val slot = slot<ViewState>()

        verifyOrder {
            observer.onChanged(ViewState.Loading())
            observer.onChanged(capture(slot))
        }

        Assert.assertNotNull(slot.captured.data)

        (slot.captured.data)?.let {
            assertThat(it, anyOf(`is`(dummyForecast1), `is`(dummyForecast2)))
        }

        coVerify { viewModel.getTramForecast() }
    }

    @Test
    fun `given error, when getting tram stop forecast, then return ViewState Error`() {

        //given
        val errorMsg = "errorMsg"
        coEvery { useCase.getTramStopForecast(MAR) } returns flowOf(
            Result.Error.BasicError(
                errorMsg = errorMsg
            )
        )
        coEvery { useCase.getTramStopForecast(STI) } returns flowOf(
            Result.Error.BasicError(
                errorMsg = errorMsg
            )
        )

        //when
        viewModel.getTramForecast()

        //then
        verifyOrder {
            observer.onChanged(ViewState.Loading())
            observer.onChanged(ViewState.Error(errorMsg))
        }
    }

    @Test
    fun `given network error, when getting tram stop forecast, then return ViewState NetworkError`() {

        //given
        coEvery { useCase.getTramStopForecast(MAR) } returns flowOf(Result.Error.NetworkError)
        coEvery { useCase.getTramStopForecast(STI) } returns flowOf(Result.Error.NetworkError)

        //when
        viewModel.getTramForecast()

        //then
        verifyOrder {
            observer.onChanged(ViewState.Loading())
            observer.onChanged(ViewState.NetworkError())
        }
    }
}