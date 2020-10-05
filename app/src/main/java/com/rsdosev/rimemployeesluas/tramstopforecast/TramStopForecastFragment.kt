package com.rsdosev.rimemployeesluas.tramstopforecast

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.rsdosev.domain.model.Forecast
import com.rsdosev.domain.model.TramStop
import com.rsdosev.rimemployeesluas.R
import com.rsdosev.rimemployeesluas.utils.*
import com.rsdosev.rimemployeesluas.utils.ViewStateType.*
import jp.wasabeef.recyclerview.animators.LandingAnimator
import kotlinx.android.synthetic.main.fragment_tram_stop_forecast.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Represents the screen of Trams forecast for given trams stop for the RIM employees. It handles loading, no network, empty and success states
 */
class TramStopForecastFragment : Fragment(R.layout.fragment_tram_stop_forecast) {

    private val viewModelStop: TramStopForecastViewModel by viewModel()

    override fun onStart() {
        super.onStart()
        viewModelStop.getTramForecast()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModelStop.viewState.observe(viewLifecycleOwner, Observer {
            renderViewState(it)
        })
    }

    private fun initViews() {
        tramsForecastView.apply {
            adapter = TramsAdapter()
            itemAnimator = LandingAnimator()
            addItemDecoration(DividerItemDecorator(resources.getDrawable(R.drawable.grey_list_divider)));
        }
        buttonRefresh.setOnClickListener {
            viewModelStop.getTramForecast()
        }
    }

    private fun renderViewState(viewState: ViewState?) {
        viewState ?: return

        when (viewState.type) {
            LOADING -> showLoading()
            COMPLETED -> {
                hideLoading()
                showForecast(
                    (viewState.data as? Forecast?)
                )
            }
            ERROR -> {
                hideLoading()
                when (viewState.error) {
                    is NoNetworkException -> showNoConnectionAvailable()
                    else -> showError(viewState.error?.message)
                }
            }
        }
    }

    private fun showForecast(forecast: Forecast?) {
        forecast ?: run {
            showEmptyState()
            return@showForecast
        }
        tramStopNameView.text = forecast.stop
        statusView.text = forecast.message

        val direction = if (forecast.stopAbbreviation == TramStop.MAR.abr)
            forecast.outboundDirection
        else
            forecast.inboundDirection

        directionView.text = "(${direction?.name?.toLowerCase()})"

        if (direction?.directions?.isNullOrEmpty() == true) {
            tramsForecastView.isVisible = false
            return
        }
        (tramsForecastView.adapter as? TramsAdapter)?.submitList(direction?.directions)
        tramsForecastView.smoothScrollToPosition(0)
    }

    private fun showError(message: String?) {
        (view as ViewGroup).hideAllChildrenExcept(buttonRefresh)
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun showNoConnectionAvailable() {
        (view as ViewGroup).hideAllChildrenExcept(buttonRefresh)
        Toast.makeText(requireContext(), getString(R.string.no_network_error_message), Toast.LENGTH_LONG).show()
    }

    private fun showEmptyState() {
        (view as ViewGroup).hideAllChildrenExcept(buttonRefresh)
        Toast.makeText(requireContext(), getString(R.string.empty_state_error_message), Toast.LENGTH_LONG).show()
    }

    private fun hideLoading() {
        loadingView.isVisible = false
        buttonRefresh.isEnabled = true

        (view as ViewGroup).showAllChildrenExcept(loadingView)
    }

    private fun showLoading() {
        loadingView.isVisible = true
        buttonRefresh.isEnabled = false

        (view as ViewGroup).hideAllChildrenExcept(loadingView)
    }
}
