package com.dellosaneil.feature

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dellosaneil.feature.currentweather.CurrentWeatherViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@OptIn(ExperimentalFoundationApi::class)
@Destination
@RootNavGraph(start = true)
@Composable
fun MainScreen() {
    val viewModel  = hiltViewModel<CurrentWeatherViewModel>()

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        3
    }
    HorizontalPager(state = pagerState) { page ->


    }
}
