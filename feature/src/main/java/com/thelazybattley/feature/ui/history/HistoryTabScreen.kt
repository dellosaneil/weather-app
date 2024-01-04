package com.thelazybattley.feature.ui.history

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.thelazybattley.feature.ui.common.CommonBackground
import com.thelazybattley.feature.ui.common.CommonDatePicker
import com.thelazybattley.feature.util.toMillis
import java.time.LocalDate

@Composable
fun HistoryTabScreen(
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val viewState by viewModel.state.collectAsState()
    val events by viewModel.events.collectAsState(initial = null)

    HistoryTabScreen(
        viewState = viewState,
        event = events,
        callbacks = viewModel
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HistoryTabScreen(
    viewState: HistoryState,
    event: HistoryEvents?,
    callbacks: HistoryCallbacks
) {
    val showDateRangePicker = remember { mutableStateOf(false) }

    CommonBackground(
        modifier = Modifier.fillMaxSize()
    ) { _ ->
        if (showDateRangePicker.value) {
            CommonDatePicker(
                yearRange = IntRange(start = 2022, endInclusive = 2024),
                initialSelectedStartDateMillis = viewState.startDate.toMillis,
                initialSelectedEndDateMillis = viewState.endDate.toMillis,
                initialDisplayMode = DisplayMode.Input,
                dateValidator = {
                    LocalDate.now().toMillis > it
                },
                onDismiss = {
                    showDateRangePicker.value = false
                }
            ) { startMillis, endMillis ->
                callbacks.selectDateRange(startMillis = startMillis, endMillis = endMillis)
            }
        }
        HistoryChart(
            modifier = Modifier,
            viewState = viewState,
            callbacks = callbacks,
            showDateRangePicker = showDateRangePicker
        )
    }
}
