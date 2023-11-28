package com.dellosaneil.feature.util

val Int.epochToMillis
    get() = this * 1000L

val Int.metersToKm
    get() = this / 1000
