package com.thelazybattley.feature.ui.today

import androidx.lifecycle.viewModelScope
import com.thelazybattley.domain.local.usecase.GetLocationUseCase
import com.thelazybattley.domain.network.usecase.GetCurrentWeatherUseCase
import com.thelazybattley.domain.network.usecase.GetDailyForecastUseCase
import com.thelazybattley.domain.network.usecase.GetHourlyForecastUseCase
import com.thelazybattley.feature.base.BaseViewModel
import com.thelazybattley.feature.di.IoDispatcher
import com.thelazybattley.feature.mapper.toData
import com.thelazybattley.feature.mapper.today
import com.thelazybattley.feature.model.currentweather.CurrentWeatherData
import com.thelazybattley.feature.model.dailyforecast.DailyForecastData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodayWeatherViewModel @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getHourlyForecastUseCase: GetHourlyForecastUseCase,
    private val getDailyForecastUseCase: GetDailyForecastUseCase,
    private val getLocationUseCase: GetLocationUseCase
) : BaseViewModel<CurrentWeatherEvents, CurrentWeatherViewState>() {

    init {
        viewModelScope.launch(context= dispatcher) {
            getLocationUseCase().collect {location ->
                if(location != null) {
                    fetchCurrentWeather(
                        longitude = location.longitude.toString(),
                        latitude = location.latitude.toString()
                    )
                    fetchDailyForecast(
                        latitude = location.latitude.toString(),
                        longitude = location.longitude.toString()
                    )
                }
            }
        }
     }

    override fun initialState() = CurrentWeatherViewState.initialState()

    private fun fetchCurrentWeather(latitude: String, longitude: String) {
        viewModelScope.launch(context = dispatcher) {
            updateState { state ->
                state.copy(
                    isLoading = true
                )
            }
            getCurrentWeatherUseCase(
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
                    it.printStackTrace()
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
            val hourly = getHourlyForecastUseCase(latitude = latitude, longitude = longitude).getOrNull() ?: return@launch
            getDailyForecastUseCase(latitude = latitude, longitude = longitude).fold(
                onSuccess = { schema ->
                    updateState { state ->
                        state.copy(
                            throwable = null,
                            dailyForecast = schema.toData(
                                hourlyForecast = hourly.toData.today(
                                    timeZone = schema.timeZone
                                ).hourly
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
