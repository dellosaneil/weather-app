package com.dellosaneil.feature.util

import kotlin.math.roundToInt

val Double.toCelcius
    get() = "$this°C"

val Double.roundTwoDecimal
    get() = (this * 100).roundToInt() / 100.0

val Double.showAsPercentage
    get() = "$this%"
