package com.dellosaneil.feature.model.currentweather

data class CurrentWeatherLocation(
    val country: String,
    val localTimeMillis: Long,
    val name: String,
    val region: String,
    val tzId: String
)
