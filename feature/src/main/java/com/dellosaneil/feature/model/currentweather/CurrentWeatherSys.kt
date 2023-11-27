package com.dellosaneil.feature.model.currentweather

data class CurrentWeatherSys(
    val country: String,
    val id: Int,
    val sunriseMillis: Long,
    val sunsetMillis: Long
)
