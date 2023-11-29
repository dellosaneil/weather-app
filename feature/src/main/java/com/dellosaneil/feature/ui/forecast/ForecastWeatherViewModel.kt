package com.dellosaneil.feature.ui.forecast

import androidx.lifecycle.viewModelScope
import com.dellosaneil.domain.network.usecase.GetHourlyForecast
import com.dellosaneil.feature.base.BaseViewModel
import com.dellosaneil.feature.di.IoDispatcher
import com.dellosaneil.feature.mapper.toDailyForecast
import com.dellosaneil.feature.model.dailyforecast.DailyForecast
import com.dellosaneil.feature.util.Coordinates.LATITUDE
import com.dellosaneil.feature.util.Coordinates.LONGITUDE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastWeatherViewModel @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val getHourlyForecast: GetHourlyForecast
) : BaseViewModel<ForecastWeatherEvents, ForecastWeatherState>() {

    override fun initialState() = ForecastWeatherState.initialState()

    init {
        viewModelScope.launch(context = dispatcher) {
            getHourlyForecast(latitude = LATITUDE, longitude = LONGITUDE).fold(
                onSuccess = { schema ->
                    val dailyForecast = schema.toDailyForecast
                    updateState { state ->
                        state.copy(
                            dailyForecast = dailyForecast,
                            minTemp = dailyForecast.minOf { it.lowestTempC },
                            maxTemp = dailyForecast.maxOf { it.highestTempC }
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
}

sealed class ForecastWeatherEvents {

}

data class ForecastWeatherState(
    val isLoading: Boolean,
    val throwable: Throwable?,
    val dailyForecast: List<DailyForecast>,
    val minTemp: Double,
    val maxTemp: Double
) {
    companion object {
        fun initialState() = ForecastWeatherState(
            isLoading = true,
            dailyForecast = emptyList(),
            throwable = null,
            minTemp = Double.MIN_VALUE,
            maxTemp = Double.MAX_VALUE
        )
    }
}
