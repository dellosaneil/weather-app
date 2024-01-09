package com.thelazybattley.common.compositionlocal

import androidx.compose.runtime.compositionLocalOf

val LocalWeatherTimeZone = compositionLocalOf<String> { error("No Time Zone Provided") }
