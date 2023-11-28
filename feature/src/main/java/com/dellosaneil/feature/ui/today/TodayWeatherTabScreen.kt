package com.dellosaneil.feature.ui.today

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dellosaneil.feature.model.currentweather.CurrentWeatherData
import com.dellosaneil.feature.model.dailyforecast.DailyForecast
import com.dellosaneil.feature.model.hourlyforecast.HourlyForecastData
import com.dellosaneil.feature.ui.common.CommonBackground
import com.dellosaneil.feature.ui.common.DashedDivider
import com.dellosaneil.feature.util.Colors
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

    val scrollState = rememberScrollState()


    CommonBackground {
        when {
            viewState.isLoading -> {

            }

            viewState.throwable != null -> {
                Text(text = viewState.throwable.message ?: "")
            }

            viewState.currentWeatherData != null && viewState.hourlyForecast != null -> {
                CurrentWeatherSummary(
                    modifier = Modifier.padding(all = 8.dp),
                    currentWeather = viewState.currentWeatherData,
                    columnScope = it
                )
                DashedDivider(
                    color = Colors.DustyGray,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                TodayWeatherHourlyForecast(
                    modifier = Modifier.padding(all = 8.dp),
                    hourlyForecastData = viewState.hourlyForecast,
                    columnScope = it
                )
                TodayWeatherDailyForecast(
                    modifier = Modifier.padding(all = 8.dp),
                    dailyForecast = viewState.dailyForecast
                )
                TodayWeatherDetails(
                    modifier = Modifier.padding(all = 8.dp),
                    currentWeatherData = viewState.currentWeatherData
                )
            }
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_2")
@Composable
private fun Screen() {
    Screen(
        viewState = CurrentWeatherViewState.initialState().copy(
            isLoading = false,
            currentWeatherData = CurrentWeatherData.dummyData(),
            hourlyForecast = HourlyForecastData.dummyData(),
            dailyForecast = listOf(
                DailyForecast.dummyData(),
                DailyForecast.dummyData(),
                DailyForecast.dummyData(),
            )
        ),
        events = null
    )
}
