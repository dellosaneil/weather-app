package com.dellosaneil.feature.model.currentweather

data class CurrentWeatherMain(
    val feelsLikeC: Double,
    val humidity: Int,
    val pressure: Int,
    val tempC: Double,
    val tempMaxC: Double,
    val tempMinC: Double
)
