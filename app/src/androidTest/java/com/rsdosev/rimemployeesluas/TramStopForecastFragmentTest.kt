package com.rsdosev.rimemployeesluas

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rsdosev.domain.model.Forecast
import com.rsdosev.domain.model.Forecast.Companion.dummyForecast1
import com.rsdosev.domain.model.Result
import com.rsdosev.rimemployeesluas.base.BaseUITest
import com.rsdosev.rimemployeesluas.base.LaunchActivity
import com.rsdosev.rimemployeesluas.base.assertToastDisplayed
import com.rsdosev.rimemployeesluas.mock.MockGetLuasForecastingUseCase
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListItemCount
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListNotEmpty
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.schibsted.spain.barista.rule.BaristaRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TramStopForecastFragmentTest: BaseUITest() {

    @get:Rule
    var activityRule = BaristaRule.create(LaunchActivity::class.java)

    @Test
    fun givenSuccessfulForecastLoadAssertTramsRecycleViewNotEmpty() {
        MockGetLuasForecastingUseCase.forecast = Result.Success(dummyForecast1)

        activityRule.launchActivity()
        assertListNotEmpty(R.id.tramsForecastView)
        assertListItemCount(R.id.tramsForecastView, 2)
    }

    @Test
    fun givenNoTramsAssertTramsRecycleViewEmpty() {
        MockGetLuasForecastingUseCase.forecast = Result.Success(
            Forecast(
                created = "",
                stop = "",
                stopAbbreviation = "",
                message = "",
                directions = listOf()
            )
        )

        activityRule.launchActivity()
        assertListItemCount(R.id.tramsForecastView, 0)
    }

    @Test
    fun givenNoNetworkAssertTramsRecycleViewEmptyAndToastErrorMessageDisplayed() {
        MockGetLuasForecastingUseCase.forecast = Result.Error.NetworkError

        activityRule.launchActivity()
        assertNotDisplayed(R.id.tramsForecastView)
        assertNotDisplayed(R.id.tramStopNameView)
        assertNotDisplayed(R.id.directionView)
        assertNotDisplayed(R.id.statusView)
        assertNotDisplayed(R.id.tramsLabelView)
        assertNotDisplayed(R.id.tramsLabelDivider)

        assertToastDisplayed(activityRule.activityTestRule.activity, "No network connection!")
    }

    @Test
    fun givenAPIErrorAssertTramsRecycleViewEmptyAndToastErrorMessageDisplayed() {
        MockGetLuasForecastingUseCase.forecast = Result.Error.ApiCallError(errorMsg = "Server error!")

        activityRule.launchActivity()
        assertNotDisplayed(R.id.tramsForecastView)
        assertNotDisplayed(R.id.tramStopNameView)
        assertNotDisplayed(R.id.directionView)
        assertNotDisplayed(R.id.statusView)
        assertNotDisplayed(R.id.tramsLabelView)
        assertNotDisplayed(R.id.tramsLabelDivider)

        assertToastDisplayed(activityRule.activityTestRule.activity, "Server error!")
    }
}