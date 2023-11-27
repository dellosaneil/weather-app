package com.thelazybattley.data.network.response.hourlyforecast

data class HourlyForecastCityResponse(
    val coord: HourlyForecastCoordResponse,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)
