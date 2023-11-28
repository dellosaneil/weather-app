package com.dellosaneil.feature.ui.forecast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dellosaneil.feature.ui.common.CommonBackground
import com.dellosaneil.feature.util.Colors

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
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Colors.Trout, Colors.Shark)
                    )
                )
                .padding(bottom = 16.dp)
                .fillMaxSize()
        ) {
            when {
                viewState.isLoading -> {

                }

                viewState.throwable != null -> {

                }

                else -> {

                }
            }

        }
    }
}

@Preview(showBackground = true, device = "id:pixel_2")
@Composable
private fun Screen() {
    Screen(viewState = ForecastWeatherState.initialState(), event = null)
}
