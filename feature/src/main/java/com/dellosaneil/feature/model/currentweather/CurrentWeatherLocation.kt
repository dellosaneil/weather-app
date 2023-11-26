package com.dellosaneil.feature.model.currentweather

data class CurrentWeatherLocation(
    val country: String,
    val localtimeEpoch: Int,
    val name: String,
    val region: String,
    val tzId: String
)
