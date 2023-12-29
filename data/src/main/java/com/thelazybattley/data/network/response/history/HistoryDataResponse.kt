package com.thelazybattley.data.network.response.history

data class HistoryDataResponse(
    val daily: HistoryDailyResponse,
    val dailyUnits: HistoryDailyUnitsResponse,
    val elevation: Double,
    val generationtimeMs: Double,
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val timezoneAbbreviation: String,
    val utcOffsetSeconds: Int
)
