package com.dellosaneil.feature.ui.forecast

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dellosaneil.feature.ui.common.CommonBackground
import com.dellosaneil.feature.util.DatePattern
import com.dellosaneil.feature.util.toDateString

@Composable
fun ForecastWeatherTabScreen() {

    val viewModel = hiltViewModel<ForecastWeatherViewModel>()
    val viewState by viewModel.state.collectAsState()
    val events by viewModel.events.collectAsState(initial = null)

    Screen(
        viewState = viewState,
        event = events
    )
}

@Composable
private fun Screen(
    viewState: ForecastWeatherState,
    event: ForecastWeatherEvents?
) {
    CommonBackground(modifier = Modifier.fillMaxSize()) {
        when {
            viewState.isLoading -> {

            }

            viewState.throwable != null -> {

            }

            else -> {
                ForecastWeatherDailySummary(
                    modifier = Modifier.padding(top = 8.dp),
                    dailyForecast = viewState.dailyForecast
                )
                ForecastWeatherTempGraph(
                    minTemp = viewState.dailyForecast[1].hourly.minOf { it.tempC },
                    maxTemp = viewState.dailyForecast[1].hourly.maxOf { it.tempC },
                    temperatures = viewState.dailyForecast[1].hourly.map { it.tempC },
                    hourlyTimeStamp = viewState.dailyForecast[1].hourly.map {
                        it.dateTimeMillis.toDateString(
                            pattern = DatePattern.HOUR_MINUTES_MERIDIEM
                        )
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_2")
@Composable
private fun PreviewScreen() {
    Screen(viewState = ForecastWeatherState.initialState(), event = null)
}
