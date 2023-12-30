package com.thelazybattley.feature.ui.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HistoryTabScreen(
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val viewState by viewModel.state.collectAsState()


}
