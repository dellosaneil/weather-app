package com.thelazybattley.data.network.response.current

data class CurrentWeatherDataResponse(
    val current: CurrentWeatherCurrentResponse,
    val currentUnits: CurrentWeatherCurrentUnitsResponse,
    val elevation: Double,
    val generationtimeMs: Double,
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val timezoneAbbreviation: String,
    val utcOffsetSeconds: Int
)
