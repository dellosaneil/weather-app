package com.thelazybattley.feature.ui.forecast

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.thelazybattley.feature.model.dailyforecast.DailyForecastDaily
import com.thelazybattley.feature.ui.common.CommonBackground
import com.thelazybattley.feature.ui.compositionlocal.LocalWeatherTimeZone
import com.thelazybattley.feature.ui.compositionlocal.WeatherTimeZone

@Composable
fun ForecastWeatherTabScreen() {

    val viewModel = hiltViewModel<ForecastWeatherViewModel>()
    val viewState by viewModel.state.collectAsState()
    val events by viewModel.events.collectAsState(initial = null)

    CompositionLocalProvider(
        LocalWeatherTimeZone provides WeatherTimeZone(
            timeZone = viewState.timeZone
        )
    ) {
        Screen(
            viewState = viewState,
            event = events,
            callbacks = viewModel
        )
    }
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
                val showMore = remember { mutableStateOf(false) }
                ForecastWeatherDailySummary(
                    modifier = Modifier.padding(top = 8.dp),
                    dailyForecast = viewState.dailyForecast,
                    selectedDailyForecast = viewState.selectedDay
                ) {
                    callbacks.daySelected(dailyForecast = it)
                    showMore.value = false
                }
                ForecastWeatherTempGraph(
                    forecast = viewState.selectedDay.hourlyForecast,
                    showMore = showMore,
                    lowestTemp = viewState.selectedDay.temperature2mMin,
                    highestTemp = viewState.selectedDay.temperature2mMax,
                )
                ForecastWeatherPrecipitationGraph(
                    forecast = viewState.selectedDay
                )
                ForecastWeatherDaily(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    dailyForecast = viewState.dailyForecast,
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
                DailyForecastDaily.dummyData().copy(
                    timeMillis = 1703156210705
                ),
                DailyForecastDaily.dummyData().copy(timeMillis = 1703156110705),
                DailyForecastDaily.dummyData().copy(timeMillis = 1703176210705),
            ),
            selectedDay = DailyForecastDaily.dummyData().copy(
                timeMillis = 1703153210705
            )
        ),
        event = null,
        callbacks = object : ForecastWeatherCallbacks {
            override fun daySelected(dailyForecast: DailyForecastDaily) {
                TODO("Not yet implemented")
            }
        }
    )
}
