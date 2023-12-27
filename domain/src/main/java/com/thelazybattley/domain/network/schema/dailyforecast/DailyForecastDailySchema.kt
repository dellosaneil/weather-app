package com.thelazybattley.domain.network.schema.dailyforecast

data class DailyForecastDailySchema(
    val daylightDuration: List<Double>,
    val precipitationProbabilityMax: List<Int>,
    val sunrise: List<Int>,
    val sunset: List<Int>,
    val temperature2mMax: List<Double>,
    val temperature2mMin: List<Double>,
    val time: List<Int>,
    val weatherCode: List<Int>
)
