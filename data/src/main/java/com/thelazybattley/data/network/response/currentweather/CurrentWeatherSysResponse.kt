package com.thelazybattley.data.network.response.currentweather

data class CurrentWeatherSysResponse(
    val country: String,
    val id: Int,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)
