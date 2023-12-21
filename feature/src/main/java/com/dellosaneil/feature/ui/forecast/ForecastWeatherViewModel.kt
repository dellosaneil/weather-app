package com.dellosaneil.feature.ui.forecast

import androidx.lifecycle.viewModelScope
import com.dellosaneil.domain.network.usecase.GetDailyForecast
import com.dellosaneil.domain.network.usecase.GetHourlyForecast
import com.dellosaneil.feature.base.BaseViewModel
import com.dellosaneil.feature.di.IoDispatcher
import com.dellosaneil.feature.mapper.toData
import com.dellosaneil.feature.model.dailyforecast.DailyForecastDaily
import com.dellosaneil.feature.util.Coordinates.LATITUDE
import com.dellosaneil.feature.util.Coordinates.LONGITUDE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastWeatherViewModel @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val getDailyForecast: GetDailyForecast,
    private val getHourlyForecast: GetHourlyForecast
) : BaseViewModel<ForecastWeatherEvents, ForecastWeatherState>(), ForecastWeatherCallbacks {

    override fun initialState() = ForecastWeatherState.initialState()

    init {
        viewModelScope.launch(context = dispatcher) {
            val dailyForecast = async {
                getDailyForecast(latitude = LATITUDE, longitude = LONGITUDE)
            }
            val hourlyForecast = async {
                getHourlyForecast(latitude = LATITUDE, longitude = LONGITUDE)
            }

            val daily = dailyForecast.await().getOrThrow()
            val hourly = hourlyForecast.await().getOrThrow()

            try {
                updateState { state ->
                    val forecast = daily.toData(hourlyForecastHourly = hourly.toData.hourly).daily
                    state.copy(
                        dailyForecast = forecast,
                        selectedDay = forecast.first()
                    )
                }

            } catch (e: Exception) {
                updateState { state ->
                    state.copy(
                        throwable = e,
                    )
                }
            }

            updateState { state ->
                state.copy(isLoading = false)
            }
        }
    }

    override fun daySelected(dailyForecast: DailyForecastDaily) {
        viewModelScope.launch {
            updateState { state ->
                state.copy(
                    selectedDay = dailyForecast
                )
            }
        }
    }
}

sealed class ForecastWeatherEvents

data class ForecastWeatherState(
    val isLoading: Boolean,
    val throwable: Throwable?,
    val dailyForecast: List<DailyForecastDaily>,
    val selectedDay: DailyForecastDaily?
) {
    companion object {
        fun initialState() = ForecastWeatherState(
            isLoading = true,
            dailyForecast = emptyList(),
            throwable = null,
            selectedDay = null
        )
    }
}
