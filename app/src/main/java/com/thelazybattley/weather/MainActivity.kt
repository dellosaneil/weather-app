package com.thelazybattley.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.thelazybattley.common.compositionlocal.LocalNavController
import com.thelazybattley.main.Screens
import com.thelazybattley.main.ui.WeatherMainScreen
import com.thelazybattley.maps.ui.MapsScreen
import com.thelazybattley.weather.ui.theme.WeatherTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherTheme {
                val navController = rememberNavController()
                CompositionLocalProvider(
                    LocalNavController provides navController
                ) {
                    NavHost(
                        startDestination = Screens.MainScreen.route,
                        navController = navController
                    ) {
                        composable(route = Screens.MainScreen.route) {
                            WeatherMainScreen()
                        }
                        composable(route = Screens.MapScreen.route) {
                            MapsScreen()
                        }
                    }
                }
            }
        }
    }
}
