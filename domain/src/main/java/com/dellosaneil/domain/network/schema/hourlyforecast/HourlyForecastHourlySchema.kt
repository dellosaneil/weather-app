package com.dellosaneil.domain.network.schema.hourlyforecast

data class HourlyForecastHourlySchema(
    val precipitationProbability: List<Int>,
    val temperature2m: List<Double>,
    val time: List<Int>,
    val weatherCode: List<Int>,
    val surfacePressure: List<Double>,
    val cloudCover: List<Int>,
    val visibility: List<Int>,
    val apparentTemperature: List<Double>,
    val relativeHumidity2m: List<Int>,
    val windSpeed10m: List<Double>,
    val windDirection10m: List<Int>,
    val precipitation: List<Double>
)
