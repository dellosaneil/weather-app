package com.dellosaneil.feature.model.currentweather

data class CurrentWeatherCurrent(
    val cloud: Int,
    val condition: CurrentWeatherCondition,
    val feelslikeC: Double,
    val humidity: Int,
    val isDay: Boolean,
    val lastUpdated: String,
    val lastUpdatedEpoch: Int,
    val precipIn: Double,
    val precipMm: Double,
    val tempC: Double,
    val windKph: Double,
)
