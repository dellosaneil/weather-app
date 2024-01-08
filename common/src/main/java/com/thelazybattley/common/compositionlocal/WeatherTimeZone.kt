package com.thelazybattley.common.compositionlocal

import androidx.compose.runtime.compositionLocalOf

data class WeatherTimeZone(val timeZone: String)

val LocalWeatherTimeZone = compositionLocalOf { WeatherTimeZone(timeZone = "") }
