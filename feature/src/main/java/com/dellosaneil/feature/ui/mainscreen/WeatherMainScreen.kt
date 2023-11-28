package com.dellosaneil.feature.ui.mainscreen

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.dellosaneil.feature.R
import com.dellosaneil.feature.ui.forecast.ForecastWeatherTabScreen
import com.dellosaneil.feature.ui.today.CurrentWeatherScreen
import com.dellosaneil.feature.util.Colors
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Destination
@RootNavGraph(start = true)
@Composable
fun WeatherMainScreen() {

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        WeatherTabs.values().size
    }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(topBar = {
        WeatherMainToolbar(location = "Davao City, Philippines")
    }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
        ) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = Colors.MidGray,
                modifier = Modifier.fillMaxWidth(),
                contentColor = Colors.Trout,
                indicator = { tabPositions ->
                    if (pagerState.currentPage < tabPositions.size) {
                        TabRowDefaults.Indicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                            color = Colors.White
                        )
                    }
                }
            ) {
                WeatherTabs.values().forEachIndexed { index, tab ->
                    Tab(
                        text = {
                            Text(
                                text = stringResource(id = tab.textRes),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Colors.White
                                )
                            )
                        },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(page = index)
                            }

                        },
                    )
                }
            }
            HorizontalPager(
                state = pagerState
            ) { page ->
                when (page) {
                    WeatherTabs.TODAY.index -> {
                        CurrentWeatherScreen()
                    }

                    WeatherTabs.FORECAST.index -> {
                        ForecastWeatherTabScreen()
                    }

                    WeatherTabs.PRECIPITATION.index -> {
                        Text("Precipitation")
                    }
                }
            }
        }
    }
}

private enum class WeatherTabs(@StringRes val textRes: Int, val index: Int) {
    TODAY(textRes = (R.string.today), index = 0),
    FORECAST(textRes = R.string.forecast, index = 1),
    PRECIPITATION(textRes = R.string.precipitation, index = 2)
}
