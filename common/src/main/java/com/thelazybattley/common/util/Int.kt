package com.thelazybattley.common.util

val Int.epochToMillis
    get() = this * 1000L

val Int.metersToKm
    get() = this / 1000

val Int.toPercentage
    get() = "$this%"
