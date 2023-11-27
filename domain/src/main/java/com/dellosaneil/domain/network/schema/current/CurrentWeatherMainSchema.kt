package com.dellosaneil.domain.network.schema.current

data class CurrentWeatherMainSchema(
    val feelsLike: Double,
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    val tempMax: Double,
    val tempMin: Double
)
