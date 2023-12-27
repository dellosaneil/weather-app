package com.thelazybattley.feature.ui.today

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.thelazybattley.feature.model.currentweather.CurrentWeatherData
import com.thelazybattley.feature.model.dailyforecast.DailyForecastData
import com.thelazybattley.feature.ui.common.CommonBackground
import com.thelazybattley.feature.ui.common.DashedDivider
import com.thelazybattley.feature.util.Colors
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun CurrentWeatherScreen() {
    val viewModel = hiltViewModel<TodayWeatherViewModel>()
    val viewState by viewModel.state.collectAsState()
    val events by viewModel.events.collectAsState(initial = null)
    Screen(
        viewState = viewState,
        events = events
    )
}

@Composable
private fun Screen(
    viewState: CurrentWeatherViewState,
    events: CurrentWeatherEvents?
) {
    CommonBackground(modifier = Modifier.fillMaxSize()) { scope ->
        when {
            viewState.isLoading -> {

            }

            viewState.throwable != null -> {
                Text(text = viewState.throwable.message ?: "")
            }

            viewState.currentWeatherData != null && viewState.dailyForecast != null -> {
                val selectedWeather =
                    remember { mutableStateOf(viewState.dailyForecast.daily.first().hourlyForecast.first()) }

                CurrentWeatherSummary(
                    modifier = Modifier.padding(all = 8.dp),
                    selectedWeather = selectedWeather.value,
                    columnScope = scope
                )

                DashedDivider(
                    color = Colors.DustyGray,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                TodayWeatherHourlyForecast(
                    modifier = Modifier.padding(all = 8.dp),
                    hourlyForecast = viewState.dailyForecast.daily.first().hourlyForecast,
                    columnScope = scope
                ) { forecast ->
                    selectedWeather.value = forecast
                }

                TodayWeatherDetails(
                    modifier = Modifier.padding(all = 8.dp),
                    selectedHour = selectedWeather.value,
                    sunset = viewState.dailyForecast.daily.first().sunset,
                    sunrise = viewState.dailyForecast.daily.first().sunrise
                )
            }
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_2")
@Composable
private fun PreviewScreen() {
    Screen(
        viewState = CurrentWeatherViewState.initialState().copy(
            isLoading = false,
            currentWeatherData = CurrentWeatherData.dummyData(),
            dailyForecast = DailyForecastData.dummyData()
        ),
        events = null
    )
}
