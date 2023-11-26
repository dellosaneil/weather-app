package com.dellosaneil.feature.ui.currentweather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.dellosaneil.feature.model.currentweather.CurrentWeatherData
import com.dellosaneil.feature.util.Colors
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@RootNavGraph(start = true)
@Composable
fun CurrentWeatherScreen(
    navigator: DestinationsNavigator
) {
    val viewModel = hiltViewModel<CurrentWeatherViewModel>()
    val viewState by viewModel.state.collectAsState()
    val events by viewModel.events.collectAsState(initial = null)
    Screen(
        viewState = viewState,
        events = events,
        callbacks = viewModel,
        navigator = navigator,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Screen(
    viewState: CurrentWeatherViewState,
    events: CurrentWeatherEvents?,
    navigator: DestinationsNavigator?,
    callbacks: CurrentWeatherCallbacks,
) {
    LaunchedEffect(Unit) {
        callbacks.fetchCurrentWeather(city = "canada")
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Colors.Trout, Colors.Shark)
                    )
                )
                .fillMaxSize()
        ) {
            when {
                viewState.isLoading -> {

                }

                viewState.throwable != null -> {
                    Text(text = viewState.throwable.message ?: "")
                }

                viewState.currentWeatherData != null -> {
                    CurrentWeatherSummary(
                        currentWeather = viewState.currentWeatherData,
                        columnScope = this
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Screen() {
    Screen(
        viewState = CurrentWeatherViewState.initialState().copy(
            isLoading = false,
            currentWeatherData = CurrentWeatherData.dummyData()
        ),
        events = null,
        navigator = null,
        callbacks = object : CurrentWeatherCallbacks {
            override fun fetchCurrentWeather(city: String) {
                TODO("Not yet implemented")
            }
        }
    )
}
