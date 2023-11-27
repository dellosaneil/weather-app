package com.dellosaneil.feature.util

import kotlin.math.roundToInt

val Double.toCelcius
    get() = "$this°C"

val Double.kelvinToCelsius
    get() = ((this - 273.15) * 10).roundToInt() / 10.0
