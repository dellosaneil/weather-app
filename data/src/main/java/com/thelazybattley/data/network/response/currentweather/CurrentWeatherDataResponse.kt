package com.thelazybattley.data.network.response.currentweather

data class CurrentWeatherDataResponse(
    val base: String,
    val clouds: CurrentWeatherCloudsResponse,
    val cod: Int,
    val coord: CurrentWeatherCoordResponse,
    val dt: Int,
    val id: Int,
    val main: CurrentWeatherMainResponse,
    val name: String,
    val sys: CurrentWeatherSysResponse,
    val timezone: Int,
    val visibility: Int,
    val weather: List<CurrentWeatherResponse>,
    val wind: CurrentWeatherWindResponse
)
