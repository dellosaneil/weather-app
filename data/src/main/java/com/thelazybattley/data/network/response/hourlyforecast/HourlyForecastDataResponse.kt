package com.thelazybattley.data.network.response.hourlyforecast

data class HourlyForecastDataResponse(
    val city: HourlyForecastCityResponse,
    val cnt: Int,
    val cod: String,
    val list: List<HourlyForecastResponse>,
    val message: Int
)
