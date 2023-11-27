package com.thelazybattley.data.network.response.hourlyforecast

data class HourlyForecastWeatherResponse(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)
