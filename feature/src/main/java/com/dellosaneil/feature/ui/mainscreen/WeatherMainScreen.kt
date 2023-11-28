package com.dellosaneil.feature.ui.mainscreen

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

    val pages = listOf(
        "Today",
        "Forecast",
        "Precipitation"
    )
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        pages.size
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
                pages.forEachIndexed { index, title ->
                    Tab(
                        text = {
                            Text(
                                text = title,
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
            ) {
                when (it) {
                    0 -> {
                        CurrentWeatherScreen()
                    }

                    1 -> {
                        Text(
                            "testing"
                        )
                    }
                }
            }
        }
    }


}
