package com.thelazybattley.data.network.response.currentweather

data class CurrentWeatherResponse(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)
