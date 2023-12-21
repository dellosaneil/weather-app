package com.thelazybattley.data.network.response.dailyforecast

data class DailyForecastDataResponse(
    val daily: DailyForecastDailyResponse,
    val dailyUnits: DailyForecastDailyUnitsResponse,
    val elevation: Int,
    val generationtimeMs: Double,
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val timezoneAbbreviation: String,
    val utcOffsetSeconds: Int
)
