package com.dellosaneil.domain.network.schema.current

data class CurrentWeatherSysSchema(
    val country: String,
    val id: Int,
    val sunrise: Int,
    val sunset: Int,
)
