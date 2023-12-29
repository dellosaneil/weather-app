package com.thelazybattley.feature.ui.forecast

import androidx.lifecycle.viewModelScope
import com.thelazybattley.domain.local.usecase.GetLocationUseCase
import com.thelazybattley.domain.network.usecase.GetDailyForecastUseCase
import com.thelazybattley.domain.network.usecase.GetHourlyForecastUseCase
import com.thelazybattley.feature.base.BaseViewModel
import com.thelazybattley.feature.di.IoDispatcher
import com.thelazybattley.feature.mapper.toData
import com.thelazybattley.feature.mapper.today
import com.thelazybattley.feature.model.dailyforecast.DailyForecastDaily
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastWeatherViewModel @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val getDailyForecastUseCase: GetDailyForecastUseCase,
    private val getHourlyForecastUseCase: GetHourlyForecastUseCase,
    private val getLocationUseCase: GetLocationUseCase
) : BaseViewModel<ForecastWeatherEvents, ForecastWeatherState>(), ForecastWeatherCallbacks {

    override fun initialState() = ForecastWeatherState.initialState()

    init {
        viewModelScope.launch(context = dispatcher) {
            getLocationUseCase().collect { location ->
                if (location == null) {
                    return@collect
                }
                initialData(
                    latitude = location.latitude.toString(),
                    longitude = location.longitude.toString(),
                    coroutineScope = this
                )
            }

        }
    }

    private suspend fun initialData(
        latitude: String,
        longitude: String,
        coroutineScope: CoroutineScope
    ) {
        with(coroutineScope) {
            val dailyForecast = async {
                getDailyForecastUseCase(
                    latitude = latitude,
                    longitude = longitude
                )
            }
            val hourlyForecast = async {
                getHourlyForecastUseCase(
                    latitude = latitude,
                    longitude = longitude
                )
            }

            try {
                val daily = dailyForecast.await().getOrThrow()
                val hourly = hourlyForecast.await().getOrThrow()
                updateState { state ->
                    val forecast = daily.toData(hourlyForecast = hourly.toData.hourly).daily
                    val firstForecast = forecast.first()
                        .copy(
                            hourlyForecast = forecast.first().hourlyForecast.today(
                                timeZone = daily.timeZone
                            )
                        )
                    state.copy(
                        timeZone = daily.timeZone,
                        dailyForecast = forecast.mapIndexed { index, item ->
                            if (index == 0) {
                                item.copy(
                                    hourlyForecast = item.hourlyForecast.today(
                                        timeZone = daily.timeZone
                                    )
                                )
                            } else {
                                item
                            }
                        },
                        selectedDay = firstForecast
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
    val selectedDay: DailyForecastDaily?,
    val timeZone: String
) {
    companion object {
        fun initialState() = ForecastWeatherState(
            isLoading = true,
            dailyForecast = emptyList(),
            throwable = null,
            selectedDay = null,
            timeZone = "GMT+8"
        )
    }
}
