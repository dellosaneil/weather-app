package com.thelazybattley.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.thelazybattley.feature.ui.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost
import com.thelazybattley.weather.ui.theme.WeatherTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}
