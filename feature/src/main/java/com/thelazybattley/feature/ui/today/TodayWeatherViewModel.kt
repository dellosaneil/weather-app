package com.thelazybattley.feature.ui.today

import androidx.lifecycle.viewModelScope
import com.thelazybattley.domain.network.usecase.GetCurrentWeather
import com.thelazybattley.domain.network.usecase.GetDailyForecast
import com.thelazybattley.domain.network.usecase.GetHourlyForecast
import com.thelazybattley.feature.base.BaseViewModel
import com.thelazybattley.feature.di.IoDispatcher
import com.thelazybattley.feature.mapper.toData
import com.thelazybattley.feature.mapper.today
import com.thelazybattley.feature.model.currentweather.CurrentWeatherData
import com.thelazybattley.feature.model.dailyforecast.DailyForecastData
import com.thelazybattley.feature.util.Coordinates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodayWeatherViewModel @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val getCurrentWeather: GetCurrentWeather,
    private val getHourlyForecast: GetHourlyForecast,
    private val getDailyForecast: GetDailyForecast
) : BaseViewModel<CurrentWeatherEvents, CurrentWeatherViewState>() {

    init {
        fetchCurrentWeather(longitude = Coordinates.LONGITUDE, latitude = Coordinates.LATITUDE)
        fetchDailyForecast(latitude = Coordinates.LATITUDE, longitude = Coordinates.LONGITUDE)
    }

    override fun initialState() = CurrentWeatherViewState.initialState()

    private fun fetchCurrentWeather(latitude: String, longitude: String) {
        viewModelScope.launch(context = dispatcher) {
            updateState { state ->
                state.copy(
                    isLoading = true
                )
            }
            getCurrentWeather(
                latitude = latitude,
                longitude = longitude
            ).fold(
                onSuccess = { schema ->
                    updateState { state ->
                        state.copy(
                            currentWeatherData = schema.toData,
                            throwable = null
                        )
                    }
                },
                onFailure = {
                    updateState { state ->
                        state.copy(
                            throwable = it
                        )
                    }
                }
            )
            updateState { state ->
                state.copy(
                    isLoading = false
                )
            }
        }
    }

    private fun fetchDailyForecast(latitude: String, longitude: String) {
        viewModelScope.launch(context = dispatcher) {
            val hourly = getHourlyForecast(latitude = latitude, longitude = longitude).getOrNull() ?: return@launch
            getDailyForecast(latitude = latitude, longitude = longitude).fold(
                onSuccess = { schema ->
                    updateState { state ->
                        state.copy(
                            throwable = null,
                            dailyForecast = schema.toData(
                                hourlyForecastHourly = hourly.toData.today.hourly
                            )
                        )
                    }
                },
                onFailure = {
                    updateState { state ->
                        state.copy(
                            throwable = it
                        )
                    }
                }
            )
            updateState { state ->
                state.copy(isLoading = false)
            }
        }
    }
}

sealed class CurrentWeatherEvents

data class CurrentWeatherViewState(
    val isLoading: Boolean,
    val throwable: Throwable?,
    val currentWeatherData: CurrentWeatherData?,
    val dailyForecast: DailyForecastData?,
) {
    companion object {
        fun initialState() = CurrentWeatherViewState(
            isLoading = true,
            currentWeatherData = null,
            throwable = null,
            dailyForecast = null
        )
    }
}
