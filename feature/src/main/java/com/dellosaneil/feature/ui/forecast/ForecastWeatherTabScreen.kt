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
import com.dellosaneil.feature.model.dailyforecast.DailyForecast
import com.dellosaneil.feature.ui.common.CommonBackground

@Composable
fun ForecastWeatherTabScreen() {

    val viewModel = hiltViewModel<ForecastWeatherViewModel>()
    val viewState by viewModel.state.collectAsState()
    val events by viewModel.events.collectAsState(initial = null)

    Screen(
        viewState = viewState,
        event = events,
        callbacks = viewModel
    )
}

@Composable
private fun Screen(
    viewState: ForecastWeatherState,
    event: ForecastWeatherEvents?,
    callbacks: ForecastWeatherCallbacks
) {
    CommonBackground(modifier = Modifier.fillMaxSize()) {
        when {
            viewState.isLoading -> {

            }

            viewState.throwable != null -> {

            }

            viewState.selectedDay != null -> {
                ForecastWeatherDailySummary(
                    modifier = Modifier.padding(top = 8.dp),
                    dailyForecast = viewState.dailyForecast,
                    selectedDailyForecast = viewState.selectedDay
                ) {
                    callbacks.daySelected(dailyForecast = it)
                }
                ForecastWeatherTempGraph(
                    forecast = viewState.selectedDay
                )
                ForecastWeatherDaily(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    dailyForecast = viewState.dailyForecast
                )
            }
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_2")
@Composable
private fun PreviewScreen() {
    Screen(
        viewState = ForecastWeatherState.initialState().copy(
            isLoading = false,
            dailyForecast = listOf(
                DailyForecast.dummyData().copy(
                    day = "Tuesday"
                ),
                DailyForecast.dummyData().copy(day = "Wednesday"),
                DailyForecast.dummyData().copy(day = "Thursday"),
            ),
            selectedDay = DailyForecast.dummyData().copy(
                day = "Tuesday"
            )
        ),
        event = null,
        callbacks = object : ForecastWeatherCallbacks {
            override fun daySelected(dailyForecast: DailyForecast) {
                TODO("Not yet implemented")
            }

        }
    )
}
