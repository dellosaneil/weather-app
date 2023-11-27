package com.thelazybattley.data.network.response.currentweather

data class CurrentWeatherMainResponse(
    val feelsLike: Double,
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    val tempMax: Double,
    val tempMin: Double
)
