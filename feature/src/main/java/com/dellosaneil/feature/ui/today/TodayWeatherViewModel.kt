package com.dellosaneil.feature.ui.today

import androidx.lifecycle.viewModelScope
import com.dellosaneil.domain.network.usecase.GetCurrentWeather
import com.dellosaneil.domain.network.usecase.GetHourlyForecast
import com.dellosaneil.feature.base.BaseViewModel
import com.dellosaneil.feature.di.IoDispatcher
import com.dellosaneil.feature.mapper.toDailyForecast
import com.dellosaneil.feature.mapper.toData
import com.dellosaneil.feature.model.currentweather.CurrentWeatherData
import com.dellosaneil.feature.model.dailyforecast.DailyForecast
import com.dellosaneil.feature.model.hourlyforecast.HourlyForecastData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodayWeatherViewModel @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val getCurrentWeather: GetCurrentWeather,
    private val getHourlyForecast: GetHourlyForecast
) : BaseViewModel<CurrentWeatherEvents, CurrentWeatherViewState>(), TodayWeatherCallbacks {

    companion object {
        private const val DEBOUNCE_DELAY = 500L
    }


    override fun initialState() = CurrentWeatherViewState.initialState()

    override fun fetchCurrentWeather(latitude: String, longitude: String) {
        viewModelScope.launch(context = dispatcher) {
            delay(DEBOUNCE_DELAY)
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

    override fun fetchHourlyForecast(latitude: String, longitude: String) {
        viewModelScope.launch(context = dispatcher) {
            getHourlyForecast(latitude = latitude, longitude = longitude).fold(
                onSuccess = { schema ->
                    updateState { state ->
                        state.copy(
                            throwable = null,
                            hourlyForecast = schema.toData,
                            dailyForecast = schema.toDailyForecast
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
    val hourlyForecast: HourlyForecastData?,
    val dailyForecast : List<DailyForecast>,
) {
    companion object {
        fun initialState() = CurrentWeatherViewState(
            isLoading = true,
            currentWeatherData = null,
            throwable = null,
            hourlyForecast = null,
            dailyForecast = emptyList()
        )
    }
}
