package com.thelazybattley.data.network.response.hourlyforecast

data class HourlyForecastResponse(
    val clouds: HourlyForecastCloudsResponse,
    val dt: Int,
    val dtTxt: String,
    val main: HourlyForecastMainResponse,
    val pop: Double,
    val rain: HourlyForecastRainResponse,
    val sys: HourlyForecastSysResponse,
    val visibility: Int,
    val weather: List<HourlyForecastWeatherResponse>,
    val wind: HourlyForecastWindResponse
)
