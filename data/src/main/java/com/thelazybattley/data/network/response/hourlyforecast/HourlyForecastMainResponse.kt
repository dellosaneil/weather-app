package com.thelazybattley.data.network.response.hourlyforecast

data class HourlyForecastMainResponse(
    val feelsLike: Double,
    val grndLevel: Int,
    val humidity: Int,
    val pressure: Int,
    val seaLevel: Int,
    val temp: Double,
    val tempKf: Double,
    val tempMax: Double,
    val tempMin: Double
)
