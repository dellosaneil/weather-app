package com.dellosaneil.feature.ui.forecast

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dellosaneil.feature.ui.common.CommonBackground

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
    CommonBackground {
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
                    minTemp = viewState.minTemp,
                    maxTemp = viewState.maxTemp,
                    maxTemps = viewState.dailyForecast.map { it.highestTempC },
                    minTemps = viewState.dailyForecast.map { it.lowestTempC },
                    dates = viewState.dailyForecast.map { it.day }
                )
            }
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_2")
@Composable
private fun Screen() {
    Screen(viewState = ForecastWeatherState.initialState(), event = null)
}
