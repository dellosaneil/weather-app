package com.thelazybattley.domain.network.schema.current

data class CurrentWeatherCurrentSchema(
    val apparentTemperature: Double,
    val isDay: Int,
    val precipitation: Double,
    val temperature2m: Double,
    val time: Int,
    val weatherCode: Int,
    val windDirection10m: Int,
    val windSpeed10m: Double,
    val cloudCover: Int,
    val surfacePressure: Double,
    val relativeHumidity2m: Int
)
