package com.dellosaneil.feature.ui.forecast

import androidx.lifecycle.viewModelScope
import com.dellosaneil.domain.network.usecase.GetHourlyForecast
import com.dellosaneil.feature.base.BaseViewModel
import com.dellosaneil.feature.di.IoDispatcher
import com.dellosaneil.feature.mapper.toDailyForecast
import com.dellosaneil.feature.model.dailyforecast.DailyForecast
import com.dellosaneil.feature.util.Coordinates.LATITUDE
import com.dellosaneil.feature.util.Coordinates.LONGITUDE
import com.dellosaneil.feature.util.DatePattern
import com.dellosaneil.feature.util.toDateString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastWeatherViewModel @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val getHourlyForecast: GetHourlyForecast
) : BaseViewModel<ForecastWeatherEvents, ForecastWeatherState>(), ForecastWeatherCallbacks {

    override fun initialState() = ForecastWeatherState.initialState()

    init {
        viewModelScope.launch(context = dispatcher) {
            getHourlyForecast(latitude = LATITUDE, longitude = LONGITUDE).fold(
                onSuccess = { schema ->
                    val dailyForecast = schema.toDailyForecast.drop(1)
                    updateState { state ->
                        val defaultForecast = dailyForecast.first()
                        state.copy(
                            dailyForecast = dailyForecast,
                            selectedDay = defaultForecast.copy(
                                highestTempC = defaultForecast.hourly.take(4).maxOf { it.tempC },
                                lowestTempC = defaultForecast.hourly.take(4).minOf { it.tempC },
                                hourly = defaultForecast.hourly.take(4),
                                temperatures = defaultForecast.temperatures.take(4),
                                timeStamp = defaultForecast.timeStamp.take(4)
                            )
                        )
                    }
                },
                onFailure = { throwable ->
                    updateState { state ->
                        state.copy(
                            throwable = throwable
                        )
                    }
                }
            )
            updateState { state ->
                state.copy(isLoading = false)
            }
        }
    }

    override fun daySelected(dailyForecast: DailyForecast) {
        viewModelScope.launch {
            updateState { state ->
                state.copy(
                    selectedDay = dailyForecast.copy(
                        highestTempC = dailyForecast.hourly.take(4).maxOf { it.tempC },
                        lowestTempC = dailyForecast.hourly.take(4).minOf { it.tempC },
                        hourly = dailyForecast.hourly.take(4),
                        timeStamp = dailyForecast.hourly.map {
                            it.dateTimeMillis.toDateString(
                                pattern = DatePattern.HOUR_MINUTES_MERIDIEM
                            )
                        }.take(4),
                        temperatures = dailyForecast.hourly.map { it.tempC }
                    )
                )
            }
        }
    }
}

sealed class ForecastWeatherEvents {

}

data class ForecastWeatherState(
    val isLoading: Boolean,
    val throwable: Throwable?,
    val dailyForecast: List<DailyForecast>,
    val selectedDay: DailyForecast?
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
