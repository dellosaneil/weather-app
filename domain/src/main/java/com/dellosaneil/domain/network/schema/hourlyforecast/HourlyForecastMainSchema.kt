package com.dellosaneil.domain.network.schema.hourlyforecast

data class HourlyForecastMainSchema(
    val feelsLike: Double,
    val humidity: Int,
    val temp: Double,
    val tempMax: Double,
    val tempMin: Double
)
