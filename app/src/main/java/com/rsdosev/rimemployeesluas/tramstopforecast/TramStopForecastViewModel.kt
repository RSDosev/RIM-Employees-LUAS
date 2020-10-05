package com.rsdosev.rimemployeesluas.tramstopforecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rsdosev.domain.model.Forecast
import com.rsdosev.domain.model.Result
import com.rsdosev.domain.model.TramStop
import com.rsdosev.domain.usecase.GetLuasForecastingUseCase
import com.rsdosev.rimemployeesluas.utils.ViewState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import java.util.Calendar.HOUR_OF_DAY
import java.util.Calendar.MINUTE

/**
 * RIMEmployeesTramForecastFragment's corresponding ViewModel, responsible for loading the forecast and
 * managing the task's lifecycle (via viewModelScope). It passes the result to LiveData object
 * which is easily manageable by the fragment because of its lifecycle awareness
 */
open class TramStopForecastViewModel constructor(
    private val getLuasForecastingUseCase: GetLuasForecastingUseCase
) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    fun getTramForecast() {
        _viewState.value = ViewState.Loading()

        viewModelScope.launch {
            getLuasForecastingUseCase.getTramStopForecast(tramStopOfInterest).collect {
                handleResult(it)
            }
        }
    }

    private val tramStopOfInterest: TramStop
        get() = if (Calendar.getInstance().run {
                val hours = get(HOUR_OF_DAY)
                val minutes = get(MINUTE)
                when {
                    hours < 12 -> true
                    hours == 12 && minutes == 0 -> true
                    else -> false
                }
            }) TramStop.MAR else TramStop.STI

    private fun handleResult(result: Result<Forecast>) {
        _viewState.value = when (result) {
            is Result.Success -> ViewState.Completed(result.data)
            is Result.Error.BasicError -> ViewState.Error(result.errorMsg)
            Result.Error.NetworkError -> ViewState.NetworkError()
            is Result.Error.ApiCallError -> ViewState.Error(result.errorMsg)
        }
    }
}
