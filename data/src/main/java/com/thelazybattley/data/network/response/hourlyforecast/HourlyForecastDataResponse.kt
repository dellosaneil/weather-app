package com.thelazybattley.data.network.response.hourlyforecast

data class HourlyForecastDataResponse(
    val elevation: Double,
    val generationtimeMs: Double,
    val hourly: HourlyForecastHourlyResponse,
    val hourlyUnits: HourlyForecastHourlyUnitsResponse,
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val timezoneAbbreviation: String,
    val utcOffsetSeconds: Int,
    val weatherCode: Int
)
