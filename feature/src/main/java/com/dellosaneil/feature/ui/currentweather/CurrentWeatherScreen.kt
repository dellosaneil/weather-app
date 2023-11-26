package com.dellosaneil.feature.ui.currentweather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun Screen(
    viewState: CurrentWeatherViewState,
    events: CurrentWeatherEvents?,
    navigator: DestinationsNavigator?,
    callbacks : CurrentWeatherCallbacks,
) {
    LaunchedEffect(Unit) {
        callbacks.fetchCurrentWeather(city = "canada")
    }

    Column(
        modifier = Modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xff484B5B), Color(0xff2C2D35))
                )
            )
            .fillMaxSize()
    ) {
        GlideImage(
            model = viewState.currentWeatherData?.current?.condition?.icon,
            contentDescription = "",
            modifier = Modifier.size(200.dp),
        )
        Text(
            text = "${viewState.currentWeatherData?.current?.tempC}°",
            style = MaterialTheme.typography.displayLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Screen() {
    Screen(
        viewState = CurrentWeatherViewState.initialState(),
        events = null,
        navigator = null,
        callbacks = object:CurrentWeatherCallbacks {
            override fun fetchCurrentWeather(city: String) {
                TODO("Not yet implemented")
            }

        }
    )
}
