package com.dellosaneil.feature.model.hourlyforecast

data class HourlyForecastMain(
    val feelsLikeC: Double,
    val humidity: Int,
    val tempC: Double,
    val tempMaxC: Double,
    val tempMinC: Double,
    val pressure: Int
)
